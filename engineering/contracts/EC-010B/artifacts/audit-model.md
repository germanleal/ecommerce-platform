# Multi-Tenant Audit Model

Los eventos auditables deben contener `eventId`, `eventType`, `tenantId`, `userId`, `timestamp`, `correlationId`, `traceId` y `result`.

Eventos mínimos: `TENANT_CREATED`, `TENANT_UPDATED`, `TENANT_ACTIVATED`, `TENANT_SUSPENDED`, `TENANT_DELETED`, `STORE_CREATED`, `STORE_UPDATED`, `USER_ASSIGNED_TO_TENANT`, `USER_REMOVED_FROM_TENANT`, `TENANT_ACCESS_DENIED`.
