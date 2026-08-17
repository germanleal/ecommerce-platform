# Identity integration map

Identity is issued by Keycloak and validated by Spring Resource Server. Identity, Orders, Commerce, Payments, Inventory, Analytics, Integrations and Administration now validate an optional `X-Tenant-Id` against the JWT tenant claim; a mismatch returns `403`.

Repositories remain responsible for resource ownership using tenant-scoped queries. Headers never override the JWT.
