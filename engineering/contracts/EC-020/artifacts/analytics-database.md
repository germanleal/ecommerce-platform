# EC-020 Parte 2 — Database

Flyway crea `analytics_events`, `analytics_sales`, `analytics_orders`, `analytics_payments`, `analytics_inventory`, `analytics_refunds` y `analytics_product_sales`.

Las claves primarias e índices incluyen tenant cuando corresponde. `analytics_events.event_id` permite deduplicación e idempotencia. Las tablas son read models propios de Analytics; no existen claves foráneas hacia dominios transaccionales.
