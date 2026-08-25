# Logging Guidelines

Logs estructurados en JSON deben incluir timestamp, service, environment, level, message, correlationId y traceId; tenantId y userId cuando correspondan. Nunca registrar passwords, JWT, refresh tokens, secretos o claves privadas.
