# Tenant Context Runtime

`TenantContextFilter` resolves the authenticated principal before application processing, stores the resolved context in `TenantContextHolder`, and clears it in `finally` after every request.
