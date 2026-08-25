CREATE TABLE integrations (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL,
    type VARCHAR(64) NOT NULL,
    provider VARCHAR(128) NOT NULL,
    endpoint VARCHAR(512) NOT NULL,
    authentication_type VARCHAR(32) NOT NULL,
    status VARCHAR(16) NOT NULL CHECK (status IN ('ACTIVE','INACTIVE','FAILED','SUSPENDED')),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT uq_integrations_tenant_provider UNIQUE (tenant_id, provider)
);
CREATE TABLE integration_executions (
    id UUID PRIMARY KEY,
    integration_id UUID NOT NULL REFERENCES integrations(id),
    tenant_id UUID NOT NULL,
    correlation_id UUID NOT NULL,
    started_at TIMESTAMPTZ NOT NULL,
    completed_at TIMESTAMPTZ,
    status VARCHAR(32) NOT NULL,
    attempt INT NOT NULL DEFAULT 1,
    error_code VARCHAR(64),
    error_message VARCHAR(512)
);
CREATE TABLE processed_integration_events (
    event_id VARCHAR(128) PRIMARY KEY,
    tenant_id UUID NOT NULL,
    correlation_id VARCHAR(128) NOT NULL,
    processed_at TIMESTAMPTZ NOT NULL
);
CREATE INDEX idx_integrations_tenant_status ON integrations(tenant_id, status);
CREATE INDEX idx_integration_executions_tenant_time ON integration_executions(tenant_id, started_at DESC);
