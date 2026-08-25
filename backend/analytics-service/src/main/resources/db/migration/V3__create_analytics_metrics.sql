CREATE TABLE analytics_metrics(event_id UUID PRIMARY KEY,tenant_id UUID NOT NULL,event_type VARCHAR(128) NOT NULL,aggregate_id UUID,order_id UUID,product_id UUID,quantity NUMERIC(19,4),amount NUMERIC(19,4),currency VARCHAR(3),occurred_at TIMESTAMPTZ NOT NULL);
CREATE INDEX idx_analytics_metrics_tenant_time ON analytics_metrics(tenant_id,occurred_at);
CREATE INDEX idx_analytics_metrics_tenant_type ON analytics_metrics(tenant_id,event_type);
CREATE INDEX idx_analytics_metrics_tenant_product ON analytics_metrics(tenant_id,product_id);
