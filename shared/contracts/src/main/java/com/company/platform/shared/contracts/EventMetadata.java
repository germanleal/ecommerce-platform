package com.company.platform.shared.contracts;

import java.time.Instant;
import java.util.Map;

/** Immutable metadata required on every integration event. */
public record EventMetadata(String eventId, String eventType, String aggregateId, String aggregateType,
                            String tenantId, String correlationId, String causationId, String producer,
                            int version, Instant occurredAt, Map<String, String> headers) {
    public EventMetadata {
        require(eventId, "eventId"); require(eventType, "eventType"); require(aggregateId, "aggregateId");
        require(aggregateType, "aggregateType"); require(tenantId, "tenantId"); require(correlationId, "correlationId");
        require(producer, "producer");
        if (version < 1) throw new IllegalArgumentException("version must be positive");
        if (occurredAt == null) throw new NullPointerException("occurredAt");
        headers = headers == null ? Map.of() : Map.copyOf(headers);
    }
    private static void require(String value, String name) { if (value == null || value.isBlank()) throw new IllegalArgumentException(name + " is required"); }
}
