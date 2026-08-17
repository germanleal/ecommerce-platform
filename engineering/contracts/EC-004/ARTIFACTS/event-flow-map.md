# EC-004 Parte 3 — Mapa de eventos

Topics observados en código: `marketplace.events`, `commerce.events`, `checkout.events`, `order.events`, `payment.events`, `inventory.events`, `fulfillment.events`, `analytics.events` e `integration.events`.

La nomenclatura actual mezcla `OrderCreatedEvent`, `PaymentCapturedEvent` y otros nombres de clase con los nombres versionados definidos en EC-004 Parte 1. Antes de aprobar Parte 3 se debe normalizar al envelope compartido y registrar schema/version por evento.

## Riesgos verificados

- Analytics deduplica por `eventId`, pero no todos los productores usan el mismo envelope.
- Los consumidores usan `DefaultErrorHandler` con retry fijo; no hay evidencia uniforme de DLQ.
- El aislamiento SQL por tenant existe en varios repositorios, pero no se ha probado entre dos tenants mediante Kafka.
