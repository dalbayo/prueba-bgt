variable "region" {
  description = "Región de AWS"
  type        = string
  default     = "us-east-1"
}

variable "environment" {
  description = "Ambiente"
  type        = string
  default     = "dev"
}

variable "vpc_id" {
  type = string
}

variable "subnet_id" {
  type = string
}

variable "profile" {
  type    = string
  default = "terraform-btg"
}

variable "key_name" {
  type = string
}

variable "my_ip" {
  type = string
}