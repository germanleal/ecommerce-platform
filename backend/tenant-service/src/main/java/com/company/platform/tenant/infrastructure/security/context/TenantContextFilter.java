package com.company.platform.tenant.infrastructure.security.context;

import jakarta.servlet.FilterChain; import jakarta.servlet.ServletException; import jakarta.servlet.http.HttpServletRequest; import jakarta.servlet.http.HttpServletResponse;
import com.company.platform.tenant.domain.tenant.Tenant;
import com.company.platform.tenant.infrastructure.security.resolver.TenantResolver;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public final class TenantContextFilter extends OncePerRequestFilter {
    private final TenantResolver resolver;
    public TenantContextFilter(TenantResolver resolver) { this.resolver = resolver; }
    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (request.getUserPrincipal() != null) {
                Tenant tenant = resolver.resolveTenant(request.getUserPrincipal());
                if (tenant != null) TenantContextHolder.setTenant(tenant.id().value(), tenant.name().value());
            }
            chain.doFilter(request, response);
        } finally { TenantContextHolder.clear(); }
    }
}
