# Tenant Authorization Design

`@RequiresTenant` requires a current tenant context. `@RequiresPermission` additionally validates the permission through `TenantAccessValidator`. No public `tenantId` parameter is used to establish context.
