terraform {
  backend "s3" {
    bucket = "btg-terraform-statep-dev"
    key    = "btg/dev/terraform.tfstate"
    region = "us-east-1"
  }
}
