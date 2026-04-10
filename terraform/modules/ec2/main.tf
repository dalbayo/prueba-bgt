############################################
# 1. DATA SOURCE PARA LA AMI
# Esto resuelve el error: "data.aws_ami.amazon_linux" has not been declared
############################################
data "aws_ami" "amazon_linux" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = ["amzn2-ami-hvm-*-x86_64-gp2"]
  }
}

############################################
# 2. SECURITY GROUP
# Esto resuelve el error: "aws_security_group.ec2_sg" has not been declared
############################################
resource "aws_security_group" "ec2_sg" {
  name        = "btg-ec2-sg-${var.environment}"
  description = "Allow SSH and Spring Boot Port"
  vpc_id      = var.vpc_id

  # Acceso SSH restringido a tu IP
  ingress {
    description = "SSH"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = [var.my_ip]
  }

  # Acceso a la App Spring Boot (Puerto 8080)
  ingress {
    description = "Spring Boot App"
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Salida a internet permitida
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "btg-sg-${var.environment}"
  }
}

############################################
# 3. INSTANCIA EC2
############################################
resource "aws_instance" "app" {
  ami                    = data.aws_ami.amazon_linux.id
  instance_type          = "t3.micro"
  subnet_id              = var.subnet_id
  associate_public_ip_address = true
  vpc_security_group_ids = [aws_security_group.ec2_sg.id]
  key_name               = var.key_name

  # Permisos para DynamoDB
  # iam_instance_profile   = var.iam_instance_profile
  iam_instance_profile = var.iam_instance_profile
  # iam_instance_profile = aws_iam_instance_profile.ec2_profile.name

  # Script de instalación de Java 17
  user_data = file("${path.module}/user_data.sh")

  tags = {
    Name = "btg-ec2-app2-${var.environment}"
  }
}
