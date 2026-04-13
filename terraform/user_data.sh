#!/bin/bash
yum update -y
yum install -y java-17-amazon-corretto aws-cli

cd /home/ec2-user

echo "Configurando entorno..."
mkdir -p /home/ec2-user/app
cd /home/ec2-user/app

# Descargar JAR desde S3
echo "Descargando app desde S3..."
aws s3 cp s3://btg-app-artifacts-dev/app/btg-backend-0.0.1-SNAPSHOT.jar app.jar

# Permisos
chown ec2-user:ec2-user app.jar

# Ejecutar app
echo "Ejecutando app..."
nohup java -jar app.jar > app.log 2>&1 &