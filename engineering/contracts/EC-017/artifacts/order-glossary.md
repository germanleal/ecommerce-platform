# Order Glossary

| Term | Definition | Example | Rule |
|---|---|---|---|
| Order | Aggregate representing a formal purchase | `ORD-2026-000123` | One tenant and store only |
| Order Item | Purchased line within an Order | 2 units of a sellable product | Positive quantity and frozen snapshot |
| Customer Order | Order created for one customer | Customer 42's order | Customer belongs to the tenant |
| Purchase | Business action that submits a cart as an order | Checkout submission | Must create a traceable order |
| Order Number | Human-readable unique order reference | `ORD-2026-000123` | Unique within the defined order namespace |
| Order Status | Current lifecycle state | `CONFIRMED` | Changes only through permitted transitions |
| Confirmation | Acceptance of the pending purchase | `OrderConfirmedEvent` | Freezes the commercial content |
| Cancellation | Authorized termination before completion | Customer cancellation | Terminal and auditable |
| Snapshot Price | Price copied at purchase time | USD 19.90 | Never recalculated from Commerce |
| Commercial Snapshot | Product and price facts captured in an item | name, SKU, amount, currency | Immutable after confirmation |

