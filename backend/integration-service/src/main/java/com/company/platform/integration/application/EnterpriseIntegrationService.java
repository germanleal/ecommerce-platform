package com.company.platform.integration.application;

import com.company.platform.integration.domain.IntegrationConfiguration;
import com.company.platform.integration.domain.IntegrationExecution;
import com.company.platform.integration.infrastructure.TenantContextProvider;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnterpriseIntegrationService {
    private final JdbcTemplate jdbc;
    private final TenantContextProvider tenant;

    public EnterpriseIntegrationService(JdbcTemplate jdbc, TenantContextProvider tenant) {
        this.jdbc = jdbc; this.tenant = tenant;
    }

    private UUID tenantId() { return tenant.currentTenantId(); }
    private static Timestamp ts(Instant value) { return value == null ? null : Timestamp.from(value); }

    @Transactional
    public IntegrationConfiguration create(String type, String provider, String endpoint, String authType) {
        validate(type, provider, endpoint, authType);
        IntegrationConfiguration value = IntegrationConfiguration.create(tenantId(), type, provider, endpoint, authType);
        jdbc.update("INSERT INTO integrations(id,tenant_id,type,provider,endpoint,authentication_type,status,created_at,updated_at) VALUES(?,?,?,?,?,?,?,?,?)",
                value.id(), value.tenantId(), value.type(), value.provider(), value.endpoint(), value.authenticationType(), value.status(), ts(value.createdAt()), ts(value.updatedAt()));
        return value;
    }

    public List<IntegrationConfiguration> list() {
        return jdbc.query("SELECT * FROM integrations WHERE tenant_id=? ORDER BY created_at DESC", this::map, tenantId());
    }
    public IntegrationConfiguration get(UUID id) {
        return jdbc.query("SELECT * FROM integrations WHERE tenant_id=? AND id=?", this::map, tenantId(), id)
                .stream().findFirst().orElseThrow(() -> new NoSuchElementException("integration not found"));
    }
    @Transactional
    public IntegrationConfiguration update(UUID id, String type, String provider, String endpoint, String authType) {
        IntegrationConfiguration old = get(id);
        validate(type, provider, endpoint, authType);
        IntegrationConfiguration value = new IntegrationConfiguration(id, old.tenantId(), type, provider, endpoint, authType,
                old.status(), old.createdAt(), Instant.now());
        jdbc.update("UPDATE integrations SET type=?,provider=?,endpoint=?,authentication_type=?,updated_at=? WHERE id=? AND tenant_id=?",
                type, provider, endpoint, authType, ts(value.updatedAt()), id, tenantId());
        return value;
    }
    @Transactional
    public IntegrationConfiguration changeStatus(UUID id, String status) {
        IntegrationConfiguration value = get(id).status(status);
        jdbc.update("UPDATE integrations SET status=?,updated_at=? WHERE id=? AND tenant_id=?", value.status(), ts(value.updatedAt()), id, tenantId());
        return value;
    }

    @Transactional
    public IntegrationExecution test(UUID id, String idempotencyKey) {
        IntegrationConfiguration config = get(id);
        String key = idempotencyKey == null || idempotencyKey.isBlank() ? UUID.randomUUID().toString() : idempotencyKey;
        UUID correlation = UUID.nameUUIDFromBytes((id + ":" + key).getBytes(java.nio.charset.StandardCharsets.UTF_8));
        List<IntegrationExecution> previous = jdbc.query("SELECT * FROM integration_executions WHERE integration_id=? AND tenant_id=? AND correlation_id=?",
                this::mapExecution, id, tenantId(), correlation);
        if (!previous.isEmpty()) return previous.getFirst();
        Instant started = Instant.now();
        String status = "ACTIVE".equals(config.status()) || "mock".equalsIgnoreCase(config.provider()) ? "SUCCESS" : "FAILED";
        String code = "SUCCESS".equals(status) ? null : "INACTIVE_INTEGRATION";
        String message = "SUCCESS".equals(status) ? null : "integration must be ACTIVE before testing";
        IntegrationExecution execution = new IntegrationExecution(UUID.randomUUID(), id, tenantId(), correlation, started, Instant.now(), status, 1, code, message);
        jdbc.update("INSERT INTO integration_executions(id,integration_id,tenant_id,correlation_id,started_at,completed_at,status,attempt,error_code,error_message) VALUES(?,?,?,?,?,?,?,?,?,?)",
                execution.id(), execution.integrationId(), execution.tenantId(), execution.correlationId(), ts(execution.startedAt()), ts(execution.completedAt()), execution.status(), execution.attempt(), execution.errorCode(), execution.errorMessage());
        return execution;
    }
    public List<IntegrationExecution> executions(UUID id) {
        get(id);
        return jdbc.query("SELECT * FROM integration_executions WHERE tenant_id=? AND integration_id=? ORDER BY started_at DESC", this::mapExecution, tenantId(), id);
    }
    public boolean markEventProcessed(String eventId, UUID eventTenant, String correlationId) {
        if (eventId == null || eventTenant == null || correlationId == null || correlationId.isBlank()) throw new IllegalArgumentException("event metadata is required");
        if (!tenantId().equals(eventTenant)) throw new SecurityException("event tenant does not match context");
        return markEventProcessedInternal(eventId, eventTenant, correlationId);
    }
    @Transactional
    public boolean markEventProcessedInternal(String eventId, UUID eventTenant, String correlationId) {
        return jdbc.update("INSERT INTO processed_integration_events(event_id,tenant_id,correlation_id,processed_at) VALUES(?,?,?,?) ON CONFLICT(event_id) DO NOTHING",
                eventId, eventTenant, correlationId, ts(Instant.now())) == 1;
    }
    private static void validate(String type, String provider, String endpoint, String authType) {
        if (blank(type) || blank(provider) || blank(endpoint) || blank(authType)) throw new IllegalArgumentException("integration fields are required");
        if (endpoint.length() > 512 || endpoint.contains("password=") || endpoint.contains("token=") || endpoint.contains("secret=")) throw new IllegalArgumentException("secrets must not be stored in endpoint");
        if (!Set.of("NONE", "BEARER", "API_KEY", "BASIC", "CLIENT_CREDENTIALS").contains(authType.toUpperCase(Locale.ROOT))) throw new IllegalArgumentException("unsupported authentication type");
    }
    private static boolean blank(String value) { return value == null || value.isBlank(); }
    private IntegrationConfiguration map(ResultSet r, int n) throws java.sql.SQLException { return new IntegrationConfiguration((UUID)r.getObject("id"),(UUID)r.getObject("tenant_id"),r.getString("type"),r.getString("provider"),r.getString("endpoint"),r.getString("authentication_type"),r.getString("status"),r.getTimestamp("created_at").toInstant(),r.getTimestamp("updated_at").toInstant()); }
    private IntegrationExecution mapExecution(ResultSet r, int n) throws java.sql.SQLException { return new IntegrationExecution((UUID)r.getObject("id"),(UUID)r.getObject("integration_id"),(UUID)r.getObject("tenant_id"),(UUID)r.getObject("correlation_id"),r.getTimestamp("started_at").toInstant(),r.getTimestamp("completed_at")==null?null:r.getTimestamp("completed_at").toInstant(),r.getString("status"),r.getInt("attempt"),r.getString("error_code"),r.getString("error_message")); }
}
