# Security Test Report — Remediation

## Executed

- Clean Docker Compose rebuild after Flyway schema isolation.
- PostgreSQL, Kafka, Keycloak, Identity and application containers running.
- OIDC discovery: `200`.
- Health endpoints `8092`–`8097`: `200`.
- Identity `/me` without token: `401`.
- Administration endpoint without token: `401`.
- No committed token/private-key pattern detected by repository scan.

## Automated smoke

`scripts/security/iam-smoke.ps1` performs real discovery and, when environment credentials are supplied, Client Credentials and Refresh Token exchanges. No credentials are committed or assumed.

## Pending

Authorization Code browser flow, real RBAC matrix, cross-tenant E2E and persisted audit events.
