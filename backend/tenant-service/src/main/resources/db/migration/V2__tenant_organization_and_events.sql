CREATE TABLE organizations (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL UNIQUE REFERENCES tenants(id),
    legal_name VARCHAR(255) NOT NULL,
    commercial_properties JSONB NOT NULL DEFAULT '{}'::jsonb
);

CREATE TABLE tenant_events (
    event_id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    actor_id UUID,
    event_type VARCHAR(80) NOT NULL,
    occurred_at TIMESTAMP WITH TIME ZONE NOT NULL,
    correlation_id VARCHAR(120) NOT NULL,
    payload JSONB NOT NULL DEFAULT '{}'::jsonb
);

CREATE INDEX idx_tenant_events_tenant_time ON tenant_events(tenant_id, occurred_at);
