package com.company.platform.aigateway.spi;
import java.util.UUID;
import org.springframework.stereotype.Component;

/** Deterministic adapter used for readiness and local development; it never calls an external model. */
@Component
public class ReferenceAIProvider implements AIProvider {
    public String id() { return "reference"; }
    public String version() { return "mvp-1"; }
    public AIResponse generate(AIRequest request) {
        long started = System.nanoTime();
        String model = request.model() == null || request.model().isBlank() ? "reference-model" : request.model();
        return new AIResponse("Reference provider response for the supplied prompt.", id(), model, AIUsage.empty(), UUID.randomUUID().toString(), request.correlationId(), (System.nanoTime() - started) / 1_000_000, "COMPLETED");
    }
}
