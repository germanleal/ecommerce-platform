# AI Context Model

Un futuro Context Provider solicitará contexto mínimo mediante API pública, validará tenant y permisos, filtrará datos sensibles y devolverá una respuesta versionada con origen, timestamp y expiración.

El contexto se compone de hechos públicos de Marketplace, Commerce, Orders, Payments, Inventory, Analytics e Integrations. Cada hecho conserva `tenantId`, `source`, `schemaVersion` y `correlationId`.
