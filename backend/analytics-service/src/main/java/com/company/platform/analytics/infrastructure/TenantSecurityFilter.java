package com.company.platform.analytics.infrastructure;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TenantSecurityFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwt && authentication.isAuthenticated()) {
            String claim = jwt.getToken().getClaimAsString("tenant_id");
            if (claim == null) claim = jwt.getToken().getClaimAsString("tenantId");
            String requestedTenant = request.getHeader("X-Tenant-Id");
            if (requestedTenant != null) {
                try { UUID.fromString(requestedTenant); } catch (IllegalArgumentException exception) { response.sendError(403, "invalid tenant context"); return; }
                if (!isPlatform(jwt) && (claim == null || !requestedTenant.equals(claim))) { response.sendError(403, "tenant context does not match token"); return; }
            }
            if (claim == null && !isPlatform(jwt)) { response.sendError(403, "tenant_id claim required"); return; }
            if (claim != null) try { UUID.fromString(claim); } catch (IllegalArgumentException exception) { response.sendError(403, "invalid tenant claim"); return; }
        }
        chain.doFilter(request, response);
    }

    static boolean isPlatform(JwtAuthenticationToken jwt) {
        if (jwt.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_PLATFORM_ADMIN") || authority.getAuthority().equals("PLATFORM_ADMIN"))) return true;
        Map<String, Object> realmAccess = jwt.getToken().getClaimAsMap("realm_access");
        Object roles = realmAccess == null ? null : realmAccess.get("roles");
        return roles instanceof Collection<?> values && values.stream().anyMatch(role -> "PLATFORM_ADMIN".equals(role));
    }
}
