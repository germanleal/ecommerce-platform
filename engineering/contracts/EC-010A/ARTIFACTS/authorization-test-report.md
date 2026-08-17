# Authorization test report

## Implemented

- JWT authorities converter for realm roles, permissions and `realm_access.roles` in Identity, Orders, Commerce and Administration.
- Method security enabled in those services.
- Permission annotations applied to Orders and Commerce endpoints.
- Administration controller protected by admin authorities.
- Identity context test and Maven compilation pass.

## Pending runtime proof

`401`, `403`, allowed role, client credentials, tenant-crossing denial and audit event delivery require a running Keycloak and real JWTs. They are not certified while Docker Desktop/Linux engine is unavailable.
