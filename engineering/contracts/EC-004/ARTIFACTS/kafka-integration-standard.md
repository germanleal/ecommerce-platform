# EC-004 — Estándar Kafka

- Claves estables por agregado; `acks=all`, idempotencia del productor y schema versionado en producción.
- Retry con backoff, límite de reintentos y dead-letter topic; nunca descartar silenciosamente.
- Commit de offset después del efecto de negocio; deduplicación por `eventId`.
- Medir lag, errores, DLQ y edad del mensaje por consumer group.
