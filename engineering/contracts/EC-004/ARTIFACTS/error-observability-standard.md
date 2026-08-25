# EC-004 — Errores y observabilidad

Todas las APIs usan `ApiError` y no devuelven stack traces ni secretos. Errores transitorios se reintentan con backoff; validación y autorización no.

Logs JSON: `timestamp`, `service`, `environment`, `level`, `traceId`, `spanId`, `correlationId`, `tenantId` y `eventId`. Propagación W3C Trace Context. Métricas mínimas: latencia, throughput, errores, saturación; Kafka agrega lag y DLQ. Alertas accionables con runbook y sin datos personales.
