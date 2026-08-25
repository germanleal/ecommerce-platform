# AI Tenant Development Rules

Antes de crear una entidad, preguntar: **¿Esta entidad pertenece a un tenant?**

Si la respuesta es sí, incluir `tenantId`, filtro de consultas, validación contextual y pruebas de aislamiento. Nunca eliminar tenantId, quitar validaciones ni crear acceso cross-tenant.
