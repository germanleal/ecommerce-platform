package com.company.platform.integration.infrastructure;

import com.company.platform.integration.application.EnterpriseIntegrationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class IntegrationEventConsumer {
    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper mapper;
    private final EnterpriseIntegrationService integrations;
    public IntegrationEventConsumer(KafkaTemplate<String, String> kafka, ObjectMapper mapper, EnterpriseIntegrationService integrations) {
        this.kafka = kafka; this.mapper = mapper; this.integrations = integrations;
    }

    @KafkaListener(topics = {"marketplace.events", "commerce.events", "order.events", "payment.events", "inventory.events", "fulfillment.events", "analytics.events"}, groupId = "integration-service")
    public void consume(String raw) throws Exception {
        JsonNode event = mapper.readTree(raw);
        String eventId = text(event, "eventId");
        String tenantId = text(event, "tenantId");
        String correlationId = text(event, "correlationId");
        if (eventId == null || tenantId == null || correlationId == null || text(event, "timestamp") == null || text(event, "sourceService") == null || !event.has("payload")) {
            throw new IllegalArgumentException("PlatformEvent metadata is incomplete");
        }
        UUID tenant = UUID.fromString(tenantId);
        if (!integrations.markEventProcessedInternal(eventId, tenant, correlationId)) return;
        kafka.send("integration.events", eventId, raw);
    }
    private static String text(JsonNode node, String field) { String value = node.path(field).asText(null); return value == null || value.isBlank() ? null : value; }
}
