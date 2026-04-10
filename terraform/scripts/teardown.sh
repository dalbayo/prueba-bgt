#!/bin/bash

set -e

echo "⚠️ Destruyendo infraestructura..."

cd ../environments/develop

terraform destroy -auto-approve

echo "🧨 Infraestructura eliminada"