package com.company.platform.tenant.domain.tenant;

public record TenantName(String value) {
    public TenantName { if (value == null || value.isBlank()) throw new IllegalArgumentException("tenant name is required"); }
}
