# Order Domain Model

## Aggregate

`Order` is the aggregate root. It owns `OrderItem` entities, customer/store/tenant references, order number, status, totals derived from the stored snapshots, timestamps and lifecycle history. It is the only object allowed to change order lifecycle or item membership.

## Entities and value objects

- `OrderItem`: identity, product reference, quantity and immutable commercial snapshot.
- `OrderHistoryEntry`: lifecycle transition, actor, reason and timestamp.
- `OrderNumber`: immutable human-readable reference.
- `Money`: amount plus currency, with non-negative amount and explicit currency.
- `OrderStatus`: lifecycle state and transition policy.
- `OrderReference`: correlation/reference to the originating Commerce cart or checkout request.
- `CustomerReference`, `StoreReference`, `TenantId`: scoped references, not duplicated aggregates.

## Invariants

The aggregate has one tenant, one customer and one store; contains one or more items; has positive quantities; has valid money; has no duplicate item identity; and preserves the snapshot used for confirmation. Cross-context data is validated at the boundary and not re-owned.

