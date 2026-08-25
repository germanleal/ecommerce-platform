package com.company.platform.shared.contracts;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;

class IntegrationContractsTest {
    @Test void eventMetadataIsImmutableAndValidated() {
        var metadata = new EventMetadata("e-1", "OrderCreated", "o-1", "Order", "t-1", "c-1", null, "order-service", 1, Instant.parse("2026-01-01T00:00:00Z"), Map.of("source", "test"));
        assertEquals("order-service", metadata.producer());
        assertThrows(UnsupportedOperationException.class, () -> metadata.headers().put("x", "y"));
    }
    @Test void apiErrorsAcceptOnlyErrorStatuses() { assertThrows(IllegalArgumentException.class, () -> new ApiError("about:blank", "Bad", 200, "X", "d", "/", "t", "c", Instant.now(), null)); }
}
