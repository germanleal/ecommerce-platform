package com.company.platform.tenant.domain.events;

import java.time.Instant; import java.util.UUID;

public record TenantEvent(UUID eventId, UUID tenantId, UUID aggregateId, String eventType, Instant timestamp, UUID userId, String correlationId, String traceId) { }
