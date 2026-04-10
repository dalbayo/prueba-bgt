#!/bin/bash

set -e  # ❗ Detiene si hay error

echo "🚀 Inicializando Terraform..."

cd ../environments/develop

terraform init -reconfigure

echo "📦 Validando configuración..."
terraform validate

echo "📊 Planificando..."
terraform plan -out=tfplan

echo "🚀 Aplicando cambios..."
terraform apply -auto-approve tfplan

echo "✅ Infraestructura creada correctamente"