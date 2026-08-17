CREATE UNIQUE INDEX IF NOT EXISTS uq_payments_tenant_order ON payments(tenant_id, order_id);
CREATE INDEX IF NOT EXISTS idx_payments_tenant_customer ON payments(tenant_id, customer_id);
CREATE INDEX IF NOT EXISTS idx_payments_tenant_status ON payments(tenant_id, status);
