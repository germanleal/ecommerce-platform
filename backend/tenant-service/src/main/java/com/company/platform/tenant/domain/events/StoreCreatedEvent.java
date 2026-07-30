package com.company.platform.tenant.domain.events;

import java.time.Instant; import java.util.UUID;

public record StoreCreatedEvent(UUID eventId, UUID tenantId, UUID aggregateId, Instant timestamp, UUID userId, String correlationId, String traceId) implements TenantEventMarker { }
