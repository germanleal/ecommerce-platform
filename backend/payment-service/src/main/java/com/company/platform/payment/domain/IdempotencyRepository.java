package com.company.platform.payment.domain;

import java.time.Instant;
import java.util.*;

public interface IdempotencyRepository {
    Optional<IdempotencyRecord> find(UUID tenantId, String operation, String key, Instant now);
    IdempotencyRecord save(IdempotencyRecord record);
    record IdempotencyRecord(UUID id, UUID tenantId, String operation, String key, String requestHash, Integer responseStatus, String responseBody, Instant createdAt, Instant expiresAt) {}
}
