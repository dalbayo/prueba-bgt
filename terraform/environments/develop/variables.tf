variable "vpc" {
  type = object({
    name                 = string
    cidr                 = string
    azs                  = list(string)
    public_subnets       = list(string)
    enable_dns_support   = bool
    enable_dns_hostnames = bool
  })
}

variable "key_name" {
  type = string
}

variable "my_ip" {
  type = string
}