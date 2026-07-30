# Order Event Review

PASS: order and checkout events use the corporate envelope (`eventId`, `eventType`, `aggregateId`, `tenantId`, `occurredAt`, version/payload metadata) and publish to `order.events` or `checkout.events`. Payment, inventory and logistics events are intentionally outside EC-017.
