package com.company.platform.tenant.domain.tenant;

import java.util.UUID;

public record TenantId(UUID value) {
    public TenantId { if (value == null) throw new IllegalArgumentException("tenant id is required"); }
    public static TenantId newId() { return new TenantId(UUID.randomUUID()); }
}
