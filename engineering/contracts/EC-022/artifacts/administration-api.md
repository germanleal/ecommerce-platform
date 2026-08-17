# EC-022 Parte 2 — Administration API

`administration-service` expone `/admin` para tenants, roles, permissions, configurations y feature flags. Las solicitudes usan DTO de tenant y payload administrativo controlado; la autoridad de tenant proviene de JWT/TenantContext.
