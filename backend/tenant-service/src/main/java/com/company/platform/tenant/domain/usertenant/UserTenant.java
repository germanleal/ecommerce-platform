package com.company.platform.tenant.domain.usertenant;

import java.time.Instant; import java.util.UUID;

public record UserTenant(UUID id, UUID userId, UUID tenantId, String role, String status, Instant createdAt) {
    public UserTenant { if(userId==null||tenantId==null) throw new IllegalArgumentException("user and tenant are required"); if(role==null||role.isBlank()) throw new IllegalArgumentException("role is required"); if(id==null) id=UUID.randomUUID(); if(createdAt==null) createdAt=Instant.now(); }
}
