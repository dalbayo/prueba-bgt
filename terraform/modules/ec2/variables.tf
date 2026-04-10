variable "vpc_id" {
  description = "ID de la VPC"
  type        = string
}

variable "subnet_id" {
  description = "Subnet donde lanzar EC2"
  type        = string
}

variable "key_name" {
  description = "Key pair para SSH"
  type        = string
}

variable "my_ip" {
  description = "Tu dirección IP pública para acceso SSH (ej: 1.2.3.4/32)"
  type        = string
}

variable "environment" {
  description = "Entorno de despliegue (dev, test, prod)"
  type        = string
}

variable "iam_instance_profile" {
  description = "Nombre del perfil de IAM para la instancia"
  type        = string
}