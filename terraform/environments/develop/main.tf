############################################
# VPC
############################################
module "vpc" {
  source = "../../modules/vpc"

  name           = var.vpc.name
  cidr           = var.vpc.cidr
  azs            = var.vpc.azs
  public_subnets = var.vpc.public_subnets

  enable_dns_hostnames = var.vpc.enable_dns_hostnames
  enable_dns_support   = var.vpc.enable_dns_support
}

############################################
# DYNAMODB
############################################
module "dynamodb" {
  source = "../../modules/dynamodb"
}

############################################
# EC2
############################################
module "ec2" {
  source = "../../modules/ec2"

  vpc_id     = module.vpc.vpc_id
  subnet_id  = module.vpc.public_subnets[0]

  key_name = var.key_name
  my_ip    = var.my_ip
}

############################################
# S3 (OPCIONAL)
############################################
module "s3" {
  source = "../../modules/s3"
}