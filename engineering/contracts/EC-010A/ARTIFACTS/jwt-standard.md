# JWT standard

Required claims consumed by the platform: `sub`, `preferred_username`, `azp`/client id, `iat`, `exp`, and `tenant_id` (or compatibility alias `tenantId`). Realm roles are mapped from Keycloak roles. Services must reject requests without a valid issuer, signature and expiry.
