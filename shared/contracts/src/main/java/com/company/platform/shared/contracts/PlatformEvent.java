package com.company.platform.shared.contracts;

import java.time.Instant;
import java.util.Objects;

/** Canonical event envelope for all platform boundaries. */
public record PlatformEvent<T>(String eventId, String tenantId, String userId, String correlationId,
                               Instant timestamp, String sourceService, T payload) {
    public PlatformEvent {
        require(eventId, "eventId"); require(tenantId, "tenantId"); require(correlationId, "correlationId"); require(sourceService, "sourceService");
        Objects.requireNonNull(timestamp, "timestamp"); Objects.requireNonNull(payload, "payload");
    }
    private static void require(String value, String field) { if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " is required"); }
}
