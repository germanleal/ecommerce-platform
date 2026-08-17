package com.company.platform.tenant.domain.events;

import com.company.platform.shared.contracts.PlatformEvent;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record TenantLifecycleEvent(String type, UUID tenantId, UUID actorId, Instant timestamp, String correlationId) {
    public PlatformEvent<Map<String, String>> envelope() {
        return new PlatformEvent<>(UUID.randomUUID().toString(), tenantId.toString(), actorId == null ? "system" : actorId.toString(), correlationId, timestamp, "tenant-service", Map.of("type", type, "tenantId", tenantId.toString()));
    }
}
