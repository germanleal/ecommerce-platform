# Order Events

Events are facts emitted after a successful aggregate transition. They carry `eventId`, `eventType`, `aggregateId`, `tenantId`, `occurredAt`, correlation metadata and a versioned payload.

| Event | When emitted | Consumers |
|---|---|---|
| OrderCreatedEvent | Draft order created from Commerce checkout | Notifications, analytics |
| OrderSubmittedEvent | Draft submitted for confirmation | Order workflow |
| OrderConfirmedEvent | Commercial purchase confirmed | Payments, Inventory |
| OrderRejectedEvent | Confirmation refused | Notifications, analytics |
| OrderCancelledEvent | Authorized cancellation | Payments, Inventory, Notifications |
| OrderExpiredEvent | Confirmation window elapsed | Notifications, analytics |
| OrderProcessingStartedEvent | Order accepted for fulfillment | Inventory, Logistics |
| OrderCompletedEvent | Fulfillment flow reports completion | Notifications, analytics |

Events are integration contracts, not commands. Payloads contain order snapshot facts required by consumers and do not expose mutable Commerce internals.

