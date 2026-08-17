package com.company.platform.shared.contracts;

import java.time.Instant;
import java.util.Map;

/** Stable RFC-7807-compatible error data for synchronous APIs. */
public record ApiError(String type, String title, int status, String code, String detail, String instance,
                       String traceId, String correlationId, Instant timestamp, Map<String, Object> parameters) {
    public ApiError { if (status < 400 || status > 599) throw new IllegalArgumentException("status must be an HTTP error status"); parameters = parameters == null ? Map.of() : Map.copyOf(parameters); }
}
