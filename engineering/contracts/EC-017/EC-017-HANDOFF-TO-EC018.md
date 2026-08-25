# EC-017 Handoff to EC-018

## Available capabilities

Order Aggregate, OrderItem, Checkout, Commercial Snapshot, lifecycle, domain events and tenant-scoped REST APIs.

## Restrictions for EC-018

Payments and Billing must consume Order and its snapshot; they must not modify Commerce, recalculate pricing, modify the Shopping Cart or cross tenant boundaries.
