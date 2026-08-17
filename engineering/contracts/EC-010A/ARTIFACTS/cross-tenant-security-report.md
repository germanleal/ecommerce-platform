# Cross-tenant security report

Implemented denial path: when `X-Tenant-Id` differs from the verified JWT tenant claim, the filter returns `403 Forbidden` before the controller executes.

Runtime tests with two real Keycloak tenants and resource IDs remain pending until Docker/Keycloak is available.
