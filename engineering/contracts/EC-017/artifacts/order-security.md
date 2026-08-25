# Order Security

The service uses Spring Security OAuth2 Resource Server for Keycloak JWTs. `SecurityTenantContextProvider` resolves `tenant_id` from the authenticated JWT and rejects requests without an authenticated tenant. Repository methods always include that tenant value.

Permissions are `ORDER_CREATE`, `ORDER_READ`, `ORDER_CONFIRM` and `ORDER_CANCEL`; API methods enforce them with `@PreAuthorize`.

