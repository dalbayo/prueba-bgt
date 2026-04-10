module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = var.module_version

  name = var.name
  cidr = var.cidr

  azs            = var.azs
  public_subnets = var.public_subnets
  map_public_ip_on_launch = true

  #  INTERNET (CLAVE)
  enable_internet_gateway = true

  #   DNS (YA LO TENÍAS)
  enable_dns_hostnames = var.enable_dns_hostnames
  enable_dns_support   = var.enable_dns_support

  # 🔥 TAGS
  tags = {
    Project = "BTG"
    Env     = "dev"
  }
}
