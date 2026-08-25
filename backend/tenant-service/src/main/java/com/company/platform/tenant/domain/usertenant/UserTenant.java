package com.company.platform.tenant.domain.usertenant;

import java.time.Instant; import java.util.UUID;

public record UserTenant(UUID id, UUID userId, UUID tenantId, String role, String status, Instant createdAt) {
    public UserTenant { if(userId==null||tenantId==null) throw new IllegalArgumentException("user and tenant are required"); if(!validRole(role)) throw new IllegalArgumentException("invalid tenant role"); if(!validStatus(status)) throw new IllegalArgumentException("invalid membership status"); if(id==null) id=UUID.randomUUID(); if(createdAt==null) createdAt=Instant.now(); }
    public static boolean validRole(String role) { return role != null && java.util.Set.of("TENANT_OWNER","TENANT_ADMIN","TENANT_OPERATOR","TENANT_VIEWER").contains(role); }
    public static boolean validStatus(String status) { return status != null && java.util.Set.of("ACTIVE","SUSPENDED","REMOVED").contains(status); }
}
