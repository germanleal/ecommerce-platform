# Tenant Isolation Design

Flujo obligatorio:

`HTTP Request → JWT Validation → Authenticated User → Tenant Resolver → Tenant Context → Application Layer`.

`TenantContext` contiene `tenantId`, `tenantName` y `tenantConfiguration`. El acceso global `PLATFORM_ADMIN` es la única excepción contextual controlada.
