package com.company.platform.tenant.domain.tenant;

import java.util.Map;

public record TenantConfiguration(Map<String, Object> values) {
    public TenantConfiguration { values = values == null ? Map.of() : Map.copyOf(values); }
}
