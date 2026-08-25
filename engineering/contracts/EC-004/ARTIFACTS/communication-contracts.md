# EC-004 — Contratos de comunicación

## Síncrono

REST/JSON sobre HTTPS, base `/api/v1`, propagación de `X-Correlation-Id`, `X-Request-Id` e `Idempotency-Key` para comandos reintentables. Las colecciones usan `data`, `page`, `size`, `total`; los errores usan `ApiError`.

## Asíncrono

Envelope `metadata` + `payload`, implementado por `IntegrationEvent`. Metadata mínima: `eventId`, `eventType`, `aggregateId`, `aggregateType`, `tenantId`, `correlationId`, `causationId`, `producer`, `version`, `occurredAt`. Naming: `<context>.<aggregate>.<event>.v<major>`. Los consumidores deduplican por `eventId`.
