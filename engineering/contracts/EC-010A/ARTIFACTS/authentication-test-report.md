# Authentication test report

## Automated validation

- `identity-service` compiles and its `UserContext` test passes.
- Existing Spring Security configurations compile in the services reviewed.
- Realm JSON is versioned and Compose references its import directory.

## Pending runtime validation

Login, invalid token, expiry, refresh, JWT claims and service-to-service client credentials require a running Keycloak instance. They cannot be certified while Docker Desktop/Linux engine is unavailable.
