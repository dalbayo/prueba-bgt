Aquí tienes el **README.md** definitivo. He consolidado la arquitectura, el código Java y la guía de infraestructura, añadiendo una sección técnica que explica el **porqué** de cada comando, fundamental para justificar las decisiones de ingeniería en un entorno Senior.

---

# BTG Pactual - Sistema de Gestión de Suscripciones (Fondos)

Este proyecto es una solución de grado empresarial para la gestión de suscripciones a fondos de inversión, construida con **Java 17**, **Spring Boot 3** y una arquitectura nativa en la nube sobre **Amazon Web Services (AWS)**.

## 🚀 Arquitectura del Sistema

El sistema implementa un microservicio desacoplado que utiliza servicios gestionados para garantizar alta disponibilidad y consistencia eventual.

* **Capa de Datos**: Amazon DynamoDB para almacenamiento NoSQL de baja latencia.
* **Capa de Mensajería**: AWS SNS (SMS) y AWS SES (Email) para notificaciones asíncronas.
* **Capa de Cómputo**: EC2 con perfiles de instancia IAM (acceso sin llaves hardcoded).
* **Infraestructura**: Terraform 1.x con módulos reutilizables.



---

## 🛠️ Tecnologías Utilizadas

* **Backend**: Java 17 (Amazon Corretto), Spring Boot 3.x, Maven.
* **Infraestructura**: Terraform (Optimizado para procesadores **AMD Ryzen 7** y **Windows PowerShell**).
* **Seguridad**: Spring Security + JWT para asegurar los endpoints REST.

---

## 📂 Estructura del Proyecto

```text
├── terraform/                # Infraestructura como Código (IaC)
│   ├── modules/              # Módulos: vpc, ec2, dynamodb, s3
│   ├── main.tf               # Orquestación de recursos
│   └── terraform.tfvars      # Definición de variables de entorno
├── src/
│   ├── main/java/com/btg/    # Lógica de negocio (Servicios y Controladores)
│   └── test/java/com/btg/    # Pruebas Unitarias y de Integración
└── .gitignore                # Exclusión de binarios de Terraform (>100MB)
```

---

## ⚙️ Guía de Infraestructura y Limpieza (PowerShell)

### 1. Limpieza y Re-creación
Para asegurar un entorno "Clean State" en Windows, siga este flujo:

```powershell
# A. Destrucción controlada
terraform destroy -auto-approve

# B. Limpieza de caché local (Comandos nativos de Windows)
if (Test-Path .terraform) { Remove-Item -Recurse -Force .terraform }
if (Test-Path terraform.tfstate) { Remove-Item -Force terraform.tfstate }

# C. Ciclo de vida completo
terraform init
terraform plan -out="btg_plan.tfplan" `
  -var="environment=dev" `
  -var="my_ip=185.104.167.220/32" `
  -var="key_name=btg-key"

terraform apply "btg_plan.tfplan"
```

---

## 🧠 ¿Por qué usamos estos comandos? (Justificación Técnica)

Como **Senior Systems Engineer**, cada comando tiene un propósito de estabilidad y seguridad:

### **¿Por qué `Remove-Item -Recurse -Force .terraform`?**
Los binarios de los *providers* de AWS superan los **600MB**. Git y GitHub tienen límites de 100MB por archivo. Al borrar esta carpeta, evitamos errores de "Large Files" en el control de versiones y forzamos a Terraform a descargar binarios limpios y compatibles con la arquitectura del procesador (AMD64).

### **¿Por qué `terraform plan -out="btg_plan.tfplan"`?**
En entornos financieros como **BTG**, nunca se debe ejecutar un `apply` directo. Generar un archivo de plan asegura que lo que se revisó en la fase de planificación sea exactamente lo que se despliegue, evitando "Race Conditions" o cambios accidentales entre el plan y la ejecución.

### **¿Por qué `Test-NetConnection` en lugar de `ping`?**
El comando `ping` usa el protocolo ICMP, que AWS bloquea por defecto en sus Security Groups. `Test-NetConnection -Port 8080` usa TCP, permitiéndonos validar si la aplicación Spring Boot está realmente escuchando, ignorando si el firewall bloquea el ICMP.

---

## 🔍 Checklist de Conectividad (Troubleshooting)

Si la IP pública no responde, verifique esta jerarquía de red:

1.  **Security Group (Capa 4)**: ¿Está el puerto 8080 abierto para `0.0.0.0/0` o su IP específica?
2.  **Internet Gateway (Capa 3)**: ¿Tiene la VPC un IGW asociado? (Sin esto, la IP pública es inútil).
3.  **Route Table**: ¿Existe la ruta `0.0.0.0/0` apuntando al IGW?
4.  **Spring Boot Config**: Verifique que `server.address` sea `0.0.0.0` en `application.yml`.



---

## 📩 Endpoints Principales

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/api/v1/suscripciones/apertura` | Vinculación a fondo y débito de saldo. |
| `POST` | `/api/v1/suscripciones/cancelar` | Cancelación y reembolso. |
| `GET` | `/api/v1/suscripciones/historial/{id}` | Histórico de movimientos del cliente. |

---

**Desarrollado por:** Daniel Barrera
**Perfil:** Senior Systems Engineer & Technical Lead