# Keycloak configuration

Realm import: `infrastructure/keycloak/realm-platform.json`.

Configured clients: `identity-service`, `tenant-service`, `marketplace-service`, `commerce-service`, `order-service` and public `web-client`. Realm roles: `PLATFORM_ADMIN`, `TENANT_ADMIN`, `USER`, `SERVICE_ACCOUNT`.

Secrets are intentionally not stored in the realm export. Confidential client secrets must be provisioned through environment/secret management before production.
