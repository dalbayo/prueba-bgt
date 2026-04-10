terraform {
  backend "s3" {
    bucket = "btg-terraform-state-dev"
    key    = "btg/dev/terraform.tfstate"
    region = "us-east-1"
  }
}
