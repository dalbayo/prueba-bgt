#!/bin/bash
yum update -y
yum install java-17-amazon-corretto -y

cd /home/ec2-user

echo "Configurando entorno..."
mkdir -p /home/ec2-user/app
chown ec2-user:ec2-user /home/ec2-user/app

# Simulación descarga app (puedes cambiarlo por S3)
echo "Descargando app..."

# Ejemplo simple
cat <<EOF > app.jar
FAKE_JAR_CONTENT
EOF

# Ejecutar app
nohup java -jar app.jar > app.log 2>&1 &
