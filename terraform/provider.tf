terraform {
  required_version = ">= 1.5.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}
provider "aws" {
  region  = var.region
  profile = var.profile

  default_tags {
    tags = {
      Project = "btg"
      Env     = var.environment
    }
  }
}

resource "aws_s3_object" "app_jar" {
  bucket = module.s3.bucket_id
  key    = "app/btg-backend.jar"
  source = "../target/btg-backend-0.0.1-SNAPSHOT.jar"

  etag = filemd5("../target/btg-backend-0.0.1-SNAPSHOT.jar")
}