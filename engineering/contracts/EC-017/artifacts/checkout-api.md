# Checkout API

- `POST /checkout` with `{ "cartId": "..." }`: executes commercial checkout and returns an order DTO.
- `POST /checkout/validate` with `{ "cartId": "..." }`: validates without creating an order.
- `GET /checkout/{orderId}`: returns the tenant-scoped order DTO.

All endpoints require authentication and `ORDER_CREATE` or `ORDER_READ` permissions. Domain entities are not serialized directly.

