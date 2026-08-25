package com.company.platform.analytics.application;

import com.fasterxml.jackson.databind.JsonNode;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AnalyticsEventConsumer {
    private final JdbcTemplate jdbc;

    public AnalyticsEventConsumer(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @Transactional
    @KafkaListener(topics = {"marketplace.events", "commerce.events", "order.events", "payment.events", "inventory.events", "fulfillment.events", "marketplace.events.DLT", "commerce.events.DLT", "order.events.DLT", "payment.events.DLT", "inventory.events.DLT", "fulfillment.events.DLT"}, groupId = "analytics-service", containerFactory = "analyticsKafkaListenerContainerFactory")
    public void onEvent(JsonNode event) {
        UUID eventId = parse(event.path("eventId").asText(null));
        UUID tenant = parse(event.path("tenantId").asText(null));
        String correlation = event.path("correlationId").asText(null);
        if (eventId == null || tenant == null || correlation == null || correlation.isBlank()) throw new IllegalArgumentException("analytics event requires eventId, tenantId and correlationId");
        if (jdbc.queryForObject("SELECT COUNT(*) FROM analytics_events WHERE event_id=?", Long.class, eventId) > 0) return;
        String type = event.path("payload").path("eventType").asText(event.path("eventType").asText("UNKNOWN"));
        JsonNode body = event.path("payload").path("data");
        if (body.isMissingNode() || body.isNull()) body = event.path("payload");
        UUID aggregate = parse(event.path("aggregateId").asText(event.path("payload").path("aggregateId").asText(null)));
        Instant at = parseInstant(event.path("timestamp").asText(event.path("occurredAt").asText(null)));
        Timestamp occurredAt = Timestamp.from(at == null ? Instant.now() : at);
        jdbc.update("INSERT INTO analytics_events(event_id,tenant_id,event_type,aggregate_id,correlation_id,occurred_at,payload) VALUES(?,?,?,?,?,?,?::jsonb)", eventId, tenant, type, aggregate, correlation, occurredAt, body.toString());
        BigDecimal amount = number(body, "total", number(body, "amount", null));
        BigDecimal quantity = number(body, "quantity", null);
        UUID order = parse(body.path("orderId").asText(null));
        UUID product = parse(body.path("productId").asText(body.path("sellableProductId").asText(null)));
        jdbc.update("INSERT INTO analytics_metrics(event_id,tenant_id,event_type,aggregate_id,order_id,product_id,quantity,amount,currency,occurred_at) VALUES(?,?,?,?,?,?,?,?,?,?)", eventId, tenant, type, aggregate, order, product, quantity, amount, body.path("currency").asText(null), occurredAt);
    }

    private BigDecimal number(JsonNode node, String key, BigDecimal fallback) { return node.path(key).isNumber() ? node.path(key).decimalValue() : fallback; }
    private UUID parse(String value) { if (value == null || value.isBlank()) return null; try { return UUID.fromString(value); } catch (IllegalArgumentException exception) { throw new IllegalArgumentException("invalid analytics UUID", exception); } }
    private Instant parseInstant(String value) { try { return value == null ? null : Instant.parse(value); } catch (Exception exception) { return null; } }
}
