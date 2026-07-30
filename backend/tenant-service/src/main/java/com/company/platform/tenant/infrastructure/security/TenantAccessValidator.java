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
        if (relations.findByUserIdAndTenantId(userId, tenantId).isEmpty()) throw new TenantAccessDeniedException("user has no access to tenant");
        if (permission != null && authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(permission) || a.getAuthority().equals("ROLE_" + permission))) throw new TenantAccessDeniedException("permission denied");
    }
}
