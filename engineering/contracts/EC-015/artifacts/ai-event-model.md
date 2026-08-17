# AI Event Model

Una futura IA podrá consumir eventos públicos ya existentes y sus versiones: catálogo/store, commerce, order, payment, inventory, fulfillment, analytics e integration. No se crean eventos nuevos en Parte 1.

Los consumidores deben validar `eventId`, `tenantId`, `eventType`, `aggregateId`, `correlationId`, `occurredAt`, `schemaVersion` y payload; deben ser idempotentes y no mutar fuentes.
