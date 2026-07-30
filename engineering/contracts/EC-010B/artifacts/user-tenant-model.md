# User-Tenant Model

La relación se modela como `User → UserTenant → Tenant`.

`UserTenant` contiene `id`, `userId`, `tenantId`, `role`, `status` y `createdAt`. Las credenciales y la identidad permanecen en Keycloak; EC-010B no crea usuarios ni passwords propios.
