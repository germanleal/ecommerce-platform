package com.company.platform.tenant.infrastructure.security.context;

import java.util.UUID;

public final class TenantContextHolder {
    private static final ThreadLocal<TenantContext> CURRENT = new ThreadLocal<>();
    private TenantContextHolder() { }
    public static void setTenant(UUID id, String name) { CURRENT.set(new TenantContext(id, null, null, name)); }
    public static void setTenant(UUID id, UUID userId, UUID organizationId, String name) { CURRENT.set(new TenantContext(id, userId, organizationId, name)); }
    public static TenantContext getTenant() { return CURRENT.get(); }
    public static void clear() { CURRENT.remove(); }
}
