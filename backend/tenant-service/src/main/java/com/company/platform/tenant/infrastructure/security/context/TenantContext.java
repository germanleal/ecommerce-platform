package com.company.platform.tenant.infrastructure.security.context;

import java.util.UUID;

public record TenantContext(UUID tenantId, UUID userId, UUID organizationId, String tenantName) { }
