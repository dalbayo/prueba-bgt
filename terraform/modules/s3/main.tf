# 1. Configuración del Provider y Versiones
terraform {
  required_version = ">= 1.5.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }

  # 2. Backend (Aquí es donde estaba el error 403, asegúrate de tener permisos)
  backend "s3" {
    bucket         = "btg-terraform-state-dev"
    key            = "btg/dev/terraform.tfstate"
    region         = "us-east-1"
    encrypt        = true
    dynamodb_table = "terraform-lock-table" # Opcional: para evitar colisiones de estado
  }
}

provider "aws" {
  region = var.region
}

# 3. Módulo de Red (VPC)
module "network" {
  source = "./modules/vpc" # Ajusta la ruta a tu carpeta de módulos

  vpc_name             = var.vpc.name
  cidr_block           = var.vpc.cidr
  availability_zones   = var.vpc.azs
  public_subnets       = var.vpc.public_subnets
  enable_dns_hostnames = var.vpc.enable_dns_hostnames
  enable_dns_support   = var.vpc.enable_dns_support
  
  environment          = var.environment
}

# 4. Módulo de Almacenamiento (El código que me mostraste hace un momento)
module "storage" {
  source = "./modules/s3"

  bucket_name = "btg-app-artifacts-dev"
  environment = var.environment
}

# 5. Security Group para SSH (Usando tu variable my_ip = "0.0.0.0/0")
resource "aws_security_group" "common_access" {
  name        = "btg-common-sg-${var.environment}"
  description = "Reglas comunes de acceso para BTG"
  vpc_id      = module.network.vpc_id

  ingress {
    description = "SSH Access"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = [var.my_ip]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "btg-sg-ssh"
  }
}

resource "aws_s3_object" "app_jar" {
  bucket = aws_s3_bucket.app_bucket.id
  key    = "app/btg-backend-0.0.1-SNAPSHOT.jar"
  source = "../target/btg-backend-0.0.1-SNAPSHOT.jar"

  etag = filemd5("../target/btg-backend-0.0.1-SNAPSHOT.jar")
}
