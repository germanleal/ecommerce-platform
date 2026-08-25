package com.company.platform.shared.contracts;

import java.time.Instant;

public record SecurityAuditEvent(String eventType, String userId, String tenantId, String resource,
                                 String action, String result, String correlationId, Instant timestamp) {
    public SecurityAuditEvent { if (eventType == null || eventType.isBlank()) throw new IllegalArgumentException("eventType is required"); if (timestamp == null) timestamp = Instant.now(); }
}
