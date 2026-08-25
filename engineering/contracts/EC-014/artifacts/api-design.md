# Marketplace API Design

Endpoints iniciales implementados: `POST /products`, `GET /products/{id}` y `POST /products/{id}/publish`. El tenant se obtiene de `TenantContextProvider`, nunca del request público.
