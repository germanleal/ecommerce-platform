# EC-004 — Estándar API

- Versionado explícito; cambios incompatibles requieren major nuevo.
- OAuth2/OIDC, scopes y tenant se validan en cada servicio.
- Timeouts, límites de payload, rate limit y circuit breaker son obligatorios entre servicios.
- No se exponen entidades de persistencia: sólo DTOs de contrato.
- `404`, `409`, `422`, `429` y `5xx` conservan códigos estables.
