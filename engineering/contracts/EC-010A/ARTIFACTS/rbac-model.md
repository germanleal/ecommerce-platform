# EC-010A Parte 2 — RBAC model

Keycloak realm roles are the source of truth: `PLATFORM_ADMIN`, `TENANT_ADMIN`, `TENANT_MANAGER`, `USER` and `SERVICE_ACCOUNT`. Backend authorization is enforced with Spring Method Security and JWT authorities; frontend checks are never trusted.
