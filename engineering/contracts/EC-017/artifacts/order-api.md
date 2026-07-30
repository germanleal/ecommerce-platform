# Order API

- `POST /orders`
- `GET /orders/{id}`
- `GET /orders/customer/{customerId}`
- `GET /orders/store/{storeId}`
- `POST /orders/{id}/confirm`
- `POST /orders/{id}/cancel`

Controllers use request/response DTOs and never return the aggregate directly. All routes require authentication and method-level permissions.

