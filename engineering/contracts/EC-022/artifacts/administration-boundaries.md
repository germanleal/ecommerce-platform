# Administration Boundaries

Enterprise Administration consume APIs/eventos públicos de los demás dominios y no accede a sus tablas. Su ownership comprende tenants, membresías, roles, permisos, configuración, flags, auditoría y preferencias administrativas.

## Subdominios

- Tenant Administration: ciclo de vida y límites del tenant.
- User/Role/Permission Administration: acceso y membresías.
- Configuration Management: parámetros globales y tenant-scoped.
- Feature Flag Management: habilitación controlada por tenant/entorno.
- Audit Center: historial inmutable de acciones.
- Notification/Monitoring Center: avisos y estado operativo administrativo.
- Platform Settings: parámetros globales gobernados.

No incluye catálogo, pedidos, pagos, inventario, KPIs ni conectores.
