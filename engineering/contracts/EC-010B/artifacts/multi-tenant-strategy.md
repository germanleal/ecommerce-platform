# Multi-Tenant Strategy

La estrategia congelada es `shared database / shared schema / tenant discriminator column`.

Toda entidad futura persistida debe incluir `tenant_id`. La separación lógica se aplica mediante contexto seguro y filtros de aislamiento; no se aceptará `tenantId` proveniente directamente del frontend, query parameters o headers públicos.
