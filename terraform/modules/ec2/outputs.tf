output "instance_public_ip" {
  description = "Dirección IP pública de la instancia EC2"
  value       = aws_instance.app.public_ip
}

output "instance_id" {
  description = "ID de la instancia EC2"
  value       = aws_instance.app.id 
}