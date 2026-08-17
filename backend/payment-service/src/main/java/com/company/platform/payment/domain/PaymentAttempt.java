package com.company.platform.payment.domain;

import java.time.Instant;
import java.util.UUID;

public record PaymentAttempt(UUID id, UUID tenantId, UUID paymentId, String provider, int attemptNumber,
                             AttemptStatus status, String providerReference, Instant requestedAt,
                             Instant completedAt, String failureReason) {
    public PaymentAttempt {
        if (tenantId == null || paymentId == null || provider == null || provider.isBlank() || attemptNumber < 1)
            throw new IllegalArgumentException("invalid payment attempt");
        if (requestedAt == null) requestedAt = Instant.now();
    }
}
