# Order Boundaries

| Context | Owns | Order relationship |
|---|---|---|
| Marketplace | Catalog, products, categories, stores, storefront | Supplies product/store references; Order does not own catalog data |
| Commerce | Sellable Product, Pricing, Commercial Rules, Shopping Cart | Order consumes cart and commercial data at checkout |
| Order Management | Order, OrderItem, confirmation, lifecycle, history | Owns the purchase record and its snapshot |
| Payments | Charges and refunds | Receives order/payment intents; owns money movement |
| Inventory | Stock and reservations | Receives item reservation requests |
| Logistics | Shipment and tracking | Receives fulfillment requests |

Order may read or consume contracts from Commerce, but never modifies Commerce aggregates. A reference to a product, cart or price is not ownership of that aggregate.

## Consistency boundary

The Order aggregate is transactionally consistent with its items and lifecycle. Commerce, Payments, Inventory and Logistics are eventually consistent integrations through contracts and events.

