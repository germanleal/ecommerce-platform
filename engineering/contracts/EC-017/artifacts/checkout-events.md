# Checkout Events

`CheckoutStartedEvent`, `CheckoutValidatedEvent`, `CommercialSnapshotCreatedEvent` and `ShoppingCartCompletedEvent` are published on `checkout.events`. `OrderGeneratedEvent` is published on the same topic and the existing `OrderCreatedEvent` is emitted on `order.events`.

All events use the corporate envelope with event id, event type, aggregate id, tenant id, occurrence timestamp, version and payload.

