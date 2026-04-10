variable "environment" {
  description = "Entorno de despliegue (ej: dev, test, prod)"
  type        = string
  # El default está bien, pero recuerda que la raíz puede sobrescribirlo
  default     = "dev" 
}