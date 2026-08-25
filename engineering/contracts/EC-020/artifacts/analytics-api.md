# EC-020 Parte 2 — Analytics API

`analytics-service` expone consultas read-only bajo `/analytics`:

- `GET /analytics/dashboard`
- `GET /analytics/sales`
- `GET /analytics/orders`
- `GET /analytics/payments`
- `GET /analytics/inventory`
- `GET /analytics/refunds`
- `GET /analytics/top-products`
- `GET /analytics/top-stores`
- `GET /analytics/top-categories`

Las consultas derivan el tenant desde JWT/TenantContext y no reciben `tenantId` como autoridad pública.
