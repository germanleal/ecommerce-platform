# Analytics Security Review

JWT y extracción de `tenant_id` están configurados; todas las rutas requieren autenticación salvo health. **Bloqueador:** no están implementados ni probados los permisos `ANALYTICS_READ`, `REPORT_READ`, `REPORT_EXPORT`, `KPI_READ` y `DASHBOARD_READ` mediante autorización efectiva.
