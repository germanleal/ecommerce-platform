package com.company.platform.tenant.domain.usertenant;

import java.time.Instant;
import java.util.UUID;

public record TenantMember(UUID id, UUID userId, UUID tenantId, String role, String status, Instant createdAt, Instant updatedAt) {
    public TenantMember(UUID userId, UUID tenantId, String role, String status, Instant createdAt) { this(UUID.randomUUID(), userId, tenantId, role, status, createdAt, createdAt); }
    public TenantMember {
        if (id == null || userId == null || tenantId == null) throw new IllegalArgumentException("membership identity is required");
        if (role == null || role.isBlank()) throw new IllegalArgumentException("role is required");
        if (status == null || status.isBlank()) throw new IllegalArgumentException("membership status is required");
        if (createdAt == null) createdAt = Instant.now();
        if (updatedAt == null) updatedAt = createdAt;
    }
}
