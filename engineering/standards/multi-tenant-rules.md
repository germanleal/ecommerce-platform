# Multi-Tenant Engineering Rules

- Toda entidad multi-tenant requiere `tenantId`.
- Todo repositorio debe aplicar filtro tenant.
- Ningún frontend define el tenant autorizado.
- Ningún servicio confía en un tenant externo sin resolverlo desde identidad y relación UserTenant.
- No modificar IAM para implementar resolución tenant.
