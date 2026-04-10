#!/bin/bash
# Actualizar paquetes
yum update -y

# Instalar Java 17
amazon-linux-extras enable corretto17
yum install -y java-17-amazon-corretto

# Verificar instalación en el log de cloud-init
java -version 2>&1

# Crear carpeta de logs para Spring Boot
mkdir -p /home/ec2-user/app/logs
chown -R ec2-user:ec2-user /home/ec2-user/app