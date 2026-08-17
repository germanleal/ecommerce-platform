# Administration Audit

AuditRecord incluye `eventId`, `tenantId`, `actorId`, `action`, `resourceType`, `resourceId`, `result`, `correlationId`, `occurredAt` y metadata no sensible. Es append-only, consultable por tenant y con retención gobernada. Nunca se registran passwords, tokens ni secretos.
