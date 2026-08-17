package com.company.platform.tenant.infrastructure.security;

import com.company.platform.tenant.domain.tenant.TenantAccessDeniedException;
import com.company.platform.tenant.domain.usertenant.UserTenantRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class TenantAccessValidator {
    private final UserTenantRepository relations;
    public TenantAccessValidator(UserTenantRepository relations) { this.relations = relations; }
    public void validate(Authentication authentication, UUID tenantId, String permission) {
        if (authentication == null || !authentication.isAuthenticated()) throw new TenantAccessDeniedException("authentication is required");
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PLATFORM_ADMIN"))) return;
        UUID userId;
        try { userId = UUID.fromString(authentication.getName()); } catch (IllegalArgumentException ex) { throw new TenantAccessDeniedException("authenticated subject is not a UUID"); }
        var relation = relations.findByUserIdAndTenantId(userId, tenantId).filter(r -> "ACTIVE".equals(r.status())).orElseThrow(() -> new TenantAccessDeniedException("user has no active access to tenant"));
        if (permission != null && authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(permission) || a.getAuthority().equals("ROLE_" + permission))) throw new TenantAccessDeniedException("permission denied");
    }
    public void validateRole(Authentication authentication, UUID tenantId, String... roles) {
        if (authentication == null || !authentication.isAuthenticated()) throw new TenantAccessDeniedException("authentication is required");
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PLATFORM_ADMIN") || a.getAuthority().equals("PLATFORM_ADMIN"))) return;
        UUID userId; try { userId = UUID.fromString(authentication.getName()); } catch (IllegalArgumentException ex) { throw new TenantAccessDeniedException("invalid authenticated subject"); }
        var role = relations.findByUserIdAndTenantId(userId, tenantId).filter(r -> "ACTIVE".equals(r.status())).map(com.company.platform.tenant.domain.usertenant.UserTenant::role).orElseThrow(() -> new TenantAccessDeniedException("active membership required"));
        for (String allowed : roles) if (allowed.equals(role)) return;
        throw new TenantAccessDeniedException("tenant role is insufficient");
    }
}
