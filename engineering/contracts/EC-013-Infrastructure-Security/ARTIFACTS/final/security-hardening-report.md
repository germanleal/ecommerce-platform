# Security Hardening Report

- Compose usa imágenes explícitas y redes separadas.
- Secretos usan variables externas y archivos locales ignorados.
- Health checks están definidos para servicios base.
- IAM y tenant isolation permanecen en sus contratos respectivos.
- No se detectaron referencias de imagen `latest`.

Pendiente: ejecución Trivy/Grype/gitleaks y pruebas de contenedor no-root en imágenes de aplicación.
