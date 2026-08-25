# Tenant Data Policy

- Toda entidad multi-tenant debe incluir `tenantId`.
- Toda consulta debe filtrar por tenant.
- Todo repositorio multi-tenant debe recibir o resolver el contexto actual.
- No se acepta `tenantId` desde frontend, query parameters o headers públicos como fuente de autoridad.
- Las FK e índices deben preservar integridad y rendimiento por tenant.
