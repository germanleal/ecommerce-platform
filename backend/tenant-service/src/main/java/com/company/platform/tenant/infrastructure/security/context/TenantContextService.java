package com.company.platform.tenant.infrastructure.security.context;

import com.company.platform.tenant.domain.tenant.TenantAccessDeniedException;
import java.util.UUID;

public interface TenantContextService {
    UUID getCurrentTenantId();
    TenantContext getCurrentTenant();
    boolean hasTenantContext();
    void clear();
}
