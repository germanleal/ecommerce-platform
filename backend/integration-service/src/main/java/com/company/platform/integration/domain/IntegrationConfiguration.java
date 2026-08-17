package com.company.platform.integration.domain;

import java.time.Instant;
import java.util.UUID;

public record IntegrationConfiguration(UUID id, UUID tenantId, String type, String provider,
        String endpoint, String authenticationType, String status, Instant createdAt, Instant updatedAt) {
    public IntegrationConfiguration {
        if (id == null || tenantId == null || blank(type) || blank(provider) || blank(endpoint)
                || blank(authenticationType) || blank(status) || createdAt == null || updatedAt == null) {
            throw new IllegalArgumentException("invalid integration configuration");
        }
    }

    private static boolean blank(String value) { return value == null || value.isBlank(); }
    public static IntegrationConfiguration create(UUID tenantId, String type, String provider, String endpoint,
            String authenticationType) {
        Instant now = Instant.now();
        return new IntegrationConfiguration(UUID.randomUUID(), tenantId, type, provider, endpoint,
                authenticationType, "INACTIVE", now, now);
    }
    public IntegrationConfiguration status(String next) {
        if (!next.matches("ACTIVE|INACTIVE|FAILED|SUSPENDED")) throw new IllegalArgumentException("invalid integration status");
        return new IntegrationConfiguration(id, tenantId, type, provider, endpoint, authenticationType, next, createdAt, Instant.now());
    }
}
