
output "ec2_public_ip" {
  # Cambia aws_instance.app por module.app_ec2
  value = module.app_ec2.instance_public_ip 
}

output "app_url" {
  value = "http://${module.app_ec2.instance_public_ip}:8080"
}