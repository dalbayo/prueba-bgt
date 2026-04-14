#!/bin/bash
# Actualiza los repositorios
yum update -y

# Amazon Corretto 17 es el estándar oficial de AWS
yum install -y java-17-amazon-corretto-devel

# El aws-cli ya suele venir instalado en la AMI de Amazon, 
# pero esto asegura que esté presente:
yum install -y aws-cli

cd /home/ec2-user

echo "Configurando entorno..."
mkdir -p /home/ec2-user/app
echo "✔ Directorio /home/ec2-user/app creado"

echo "📥 Descargando app desde S3..."
until aws s3 ls s3://btg-terraform-statep-dev; do
  echo "Esperando S3..."
  sleep 5
done
# Descargar JAR
aws s3 cp s3://btg-terraform-statep-dev/app.jar /home/ec2-user/app/app.jar


# Verificar si el archivo existe
if [ -f /home/ec2-user/app/app.jar ]; then
    echo "✔ DESCARGA EXITOSA: app.jar existe en /home/ec2-user/app"

    ls -lh /home/ec2-user/app/app.jar

else
    echo "❌ ERROR: No se descargó el archivo app.jar"
    exit 1
fi

echo "🔐 Asignando permisos..."

chown ec2-user:ec2-user /home/ec2-user/app/app.jar

if [ $? -eq 0 ]; then
    echo "✔ Permisos asignados correctamente"
else
    echo "❌ ERROR al asignar permisos"
    exit 1
fi

echo "▶ Ejecutando aplicación..."

nohup java -jar /home/ec2-user/app/app.jar > /home/ec2-user/app/app.log 2>&1 &

if [ $? -eq 0 ]; then
    echo "✔ Aplicación iniciada correctamente"
else
    echo "❌ ERROR al iniciar la aplicación"
    exit 1
fi

echo "🎉 PROCESO COMPLETADO"