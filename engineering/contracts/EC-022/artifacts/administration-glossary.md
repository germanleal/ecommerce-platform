# Administration Ubiquitous Language

| Concepto | Definición | Regla |
|---|---|---|
| Administrator | Actor autorizado para gobernar una capacidad | Requiere permiso explícito |
| Tenant Administrator | Administra un tenant | No cruza tenants |
| Platform Administrator | Administra configuración global | Excepción auditada |
| Feature Flag | Interruptor versionado de capacidad | Default seguro y rollout controlado |
| System Configuration | Parámetro gobernado de plataforma | Tiene versión y scope |
| Global Configuration | Configuración común | Solo Platform Admin |
| Audit Record | Registro inmutable de acción | Append-only y tenant-scoped |
| Permission | Capacidad atómica | Se asigna mediante roles |
| Role | Conjunto de permisos | Scope explícito |
| User Profile | Metadatos de usuario | No contiene credenciales |
| Monitoring | Estado observado de la plataforma | Read-only para administradores autorizados |
| Notification | Aviso administrativo | Respeta tenant y preferencias |
