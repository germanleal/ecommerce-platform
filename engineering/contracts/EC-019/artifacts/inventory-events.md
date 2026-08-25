# Inventory Events

Eventos publicados por Inventory/Fulfillment:

- `InventoryReservedEvent`;
- `InventoryReleasedEvent`;
- `InventoryAdjustedEvent`;
- `FulfillmentStartedEvent`;
- `PickingCompletedEvent`;
- `PackingCompletedEvent`.

Todos deben incluir `eventId`, `eventType`, `schemaVersion`, `tenantId`, `aggregateId`, `correlationId`, `causationId`, `occurredAt` y `payload`.

Inventory consume eventos versionados de Order y Payments. Los consumidores deben ser idempotentes y no depender de tablas remotas.
