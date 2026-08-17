# Tenant context design

`TenantJwtTenantResolver` obtiene el tenant exclusivamente desde el JWT verificado (`tenant_id` o `tenantId`) y lo coloca en `TenantContextHolder` por request. El contexto se limpia siempre en `finally` para evitar fuga entre requests.

El header no es una fuente de autoridad. La pertenencia y los permisos se validan con `TenantAccessValidator` contra `user_tenants`.
