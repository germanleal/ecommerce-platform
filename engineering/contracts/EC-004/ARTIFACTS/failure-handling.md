# EC-004 Parte 3 — Manejo de fallos

## Implementado parcialmente

- Retry con backoff/fixed backoff en varios listeners.
- Tablas de retry y dead-letter lógico en `integration-service`.
- Idempotencia de operaciones HTTP de Payments mediante `idempotency_keys`.
- Deduplicación de eventos en Analytics mediante `event_id`.

## Brechas que impiden aprobar

- DLQ Kafka explícito y observable no está configurado uniformemente.
- No todos los consumidores tienen deduplicación persistente por `(tenant_id,event_id)`.
- Falta auditoría uniforme de cada reintento y recuperación manual.
- Falta contrato de error común aplicado en todos los servicios.
