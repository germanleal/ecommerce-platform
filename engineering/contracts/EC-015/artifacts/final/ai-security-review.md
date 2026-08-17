# AI Security Review

JWT/TenantContext is configured and records are tenant-scoped. **Blocker:** Keycloak permissions, policy enforcement through all public endpoints and cross-tenant tests have not been verified with a real realm.
