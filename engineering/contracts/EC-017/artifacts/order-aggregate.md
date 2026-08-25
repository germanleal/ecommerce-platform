# Order Aggregate Implementation

`Order` is the aggregate root and owns `OrderItem` entities. It enforces tenant, customer, store, item, currency, subtotal and lifecycle invariants. Items contain a product name/reference and frozen `unitPrice`/`subtotal` snapshot; no item calls Commerce after creation.

The application service exposes create, confirm, cancel and query use cases. Domain transitions reject invalid or terminal-state mutations.

