# EC-004 — Catálogo inicial de eventos

| Evento | Productor | Consumidores previstos |
|---|---|---|
| `catalog.product.updated.v1` | catalog/product | search, analytics |
| `cart.checked-out.v1` | cart/checkout | order, analytics |
| `order.created.v1` | order | payment, inventory, notification, analytics |
| `payment.authorized.v1` | payment | order, notification, analytics |
| `inventory.reserved.v1` | inventory | order, shipping |
| `shipment.dispatched.v1` | shipping | order, notification, analytics |
| `user.created.v1` | identity | customer, notification, audit |

Especificación inicial: antes de `APPROVED` cada evento requiere schema, compatibilidad, productor y prueba de consumidor en el repositorio.
