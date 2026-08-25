package com.company.platform.tenant.infrastructure.security.context;

import com.company.platform.tenant.domain.tenant.TenantAccessDeniedException;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class DefaultTenantContextService implements TenantContextService {
    @Override public UUID getCurrentTenantId() { var context = getCurrentTenant(); if (context == null) throw new TenantAccessDeniedException("tenant context is required"); return context.tenantId(); }
    @Override public TenantContext getCurrentTenant() { return TenantContextHolder.getTenant(); }
    @Override public boolean hasTenantContext() { return getCurrentTenant() != null; }
    @Override public void clear() { TenantContextHolder.clear(); }
}
