package com.company.platform.analytics.infrastructure;

import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SecurityTenantContextProvider implements TenantContextProvider {
    @Override
    public UUID currentTenantId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken jwt) || !authentication.isAuthenticated()) throw new IllegalStateException("authenticated tenant context required");
        var attributes = RequestContextHolder.getRequestAttributes();
        if (TenantSecurityFilter.isPlatform(jwt) && attributes instanceof ServletRequestAttributes servlet) {
            String requestedTenant = servlet.getRequest().getHeader("X-Tenant-Id");
            if (requestedTenant != null && !requestedTenant.isBlank()) return UUID.fromString(requestedTenant);
        }
        Object tenant = jwt.getToken().getClaims().getOrDefault("tenant_id", jwt.getToken().getClaims().get("tenantId"));
        if (tenant == null) throw new IllegalStateException("tenant_id claim required");
        return UUID.fromString(tenant.toString());
    }
}
