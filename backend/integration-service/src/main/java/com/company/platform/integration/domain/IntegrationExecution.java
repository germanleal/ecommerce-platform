package com.company.platform.integration.domain;

import java.time.Instant;
import java.util.UUID;

public record IntegrationExecution(UUID id, UUID integrationId, UUID tenantId, UUID correlationId,
        Instant startedAt, Instant completedAt, String status, int attempt, String errorCode, String errorMessage) {}
