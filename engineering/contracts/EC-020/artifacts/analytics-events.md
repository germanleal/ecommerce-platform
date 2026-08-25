# EC-020 Parte 2 — Event Consumers

El consumidor está preparado para `marketplace.events`, `commerce.events`, `order.events`, `payment.events`, `inventory.events` y `fulfillment.events`.

Usa grupo `analytics-service`, commit manual, retry con backoff fijo y error handler. Los eventos deben contener `eventId`, `tenantId`, `eventType`, `aggregateId`, `correlationId`, `occurredAt` y `payload`.
