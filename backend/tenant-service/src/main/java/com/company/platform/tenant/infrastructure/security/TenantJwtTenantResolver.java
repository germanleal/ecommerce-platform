package com.company.platform.tenant.infrastructure.security;

import com.company.platform.tenant.infrastructure.security.context.TenantContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.company.platform.tenant.domain.tenant.TenantRepository;
import com.company.platform.tenant.domain.usertenant.UserTenantRepository;

import java.io.IOException;
import java.util.UUID;

public final class TenantJwtTenantResolver extends OncePerRequestFilter {
    private final TenantRepository tenants; private final UserTenantRepository memberships;
    public TenantJwtTenantResolver(TenantRepository tenants, UserTenantRepository memberships) { this.tenants=tenants; this.memberships=memberships; }
    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
                String value = jwt.getClaimAsString("tenant_id");
                if (value == null) value = jwt.getClaimAsString("tenantId");
                if (value != null) {
                    UUID tenantId;
                    try { tenantId = UUID.fromString(value); } catch (IllegalArgumentException ex) { response.sendError(403, "invalid tenant claim"); return; }
                    UUID userId;
                    try { userId = UUID.fromString(jwt.getSubject()); } catch (Exception ex) { response.sendError(403, "invalid identity"); return; }
                    boolean platformAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PLATFORM_ADMIN") || a.getAuthority().equals("PLATFORM_ADMIN"));
                    var tenant = tenants.findById(new com.company.platform.tenant.domain.tenant.TenantId(tenantId)).orElse(null);
                    if (tenant == null) {
                        if (platformAdmin) { chain.doFilter(request, response); return; }
                        response.sendError(403, "tenant membership required"); return;
                    }
                    if (!platformAdmin && memberships.findByUserIdAndTenantId(userId, tenantId).filter(m -> "ACTIVE".equals(m.status())).isEmpty()) { response.sendError(403, "tenant membership required"); return; }
                    TenantContextHolder.setTenant(tenantId, userId, tenant.organizationId(), tenant.name().value());
                }
            }
            chain.doFilter(request, response);
        } finally { TenantContextHolder.clear(); }
    }
}
