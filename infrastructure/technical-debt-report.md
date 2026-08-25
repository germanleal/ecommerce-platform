# Technical Debt Report

## Alta prioridad

- Ejecutar Trivy/Grype/gitleaks en entorno controlado antes de publicar.
- Completar pruebas E2E de IAM y aislamiento multi-tenant con servicios reales.
- Integrar métricas Micrometer y exportación OpenTelemetry en servicios.

## Media prioridad

- Añadir dashboards Grafana provisionados.
- Ejecutar pruebas de restore periódicas.
- Endurecer usuarios no-root en imágenes de aplicación.

## Baja prioridad

- Automatizar SLOs por ambiente.
- Preparar migración a Secret Manager.
