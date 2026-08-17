package com.company.platform.shared.contracts;

import java.util.Objects;

/** Envelope used by Kafka or an equivalent event transport. */
public record IntegrationEvent<T>(EventMetadata metadata, T payload) {
    public IntegrationEvent { Objects.requireNonNull(metadata, "metadata"); Objects.requireNonNull(payload, "payload"); }
}
