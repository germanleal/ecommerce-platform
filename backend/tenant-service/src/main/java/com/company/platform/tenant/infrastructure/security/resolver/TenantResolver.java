package com.company.platform.tenant.infrastructure.security.resolver;

import com.company.platform.tenant.domain.tenant.Tenant;
import java.security.Principal;

public interface TenantResolver { Tenant resolveTenant(Principal principal); }
