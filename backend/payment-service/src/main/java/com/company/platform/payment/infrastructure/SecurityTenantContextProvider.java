package com.company.platform.payment.infrastructure;

import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class SecurityTenantContextProvider implements TenantContextProvider {
    public UUID currentTenantId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken jwt) || !authentication.isAuthenticated())
            throw new IllegalStateException("authenticated tenant context required");
        Object value = jwt.getToken().getClaims().getOrDefault("tenant_id", jwt.getToken().getClaims().get("tenantId"));
        if (value == null) throw new IllegalStateException("tenant_id claim required");
        return UUID.fromString(value.toString());
    }
}
