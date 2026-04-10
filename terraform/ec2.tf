module "app_ec2" {
  source               = "./modules/ec2"
  vpc_id               = var.vpc_id
  subnet_id            = var.subnet_id
  key_name             = var.key_name
  my_ip                = var.my_ip
  iam_instance_profile = aws_iam_instance_profile.ec2_profile.name

  # ASEGÚRATE DE PASAR ESTA LÍNEA:
  environment          = var.environment 
}
