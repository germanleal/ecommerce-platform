CREATE TABLE payments (
  id UUID PRIMARY KEY,
  tenant_id UUID NOT NULL,
  order_id UUID NOT NULL,
  customer_id UUID,
  payment_reference VARCHAR(128) NOT NULL,
  status VARCHAR(32) NOT NULL,
  currency VARCHAR(3) NOT NULL,
  amount NUMERIC(19,4) NOT NULL CHECK (amount > 0),
  payment_method VARCHAR(32) NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
  CONSTRAINT uq_payment_reference UNIQUE (tenant_id, payment_reference)
);
CREATE INDEX idx_payments_tenant_order ON payments(tenant_id, order_id);

CREATE TABLE payment_attempts (
  id UUID PRIMARY KEY,
  tenant_id UUID NOT NULL,
  payment_id UUID NOT NULL REFERENCES payments(id),
  provider VARCHAR(64) NOT NULL,
  attempt_number INTEGER NOT NULL CHECK (attempt_number > 0),
  status VARCHAR(32) NOT NULL,
  provider_reference VARCHAR(256),
  requested_at TIMESTAMP WITH TIME ZONE NOT NULL,
  completed_at TIMESTAMP WITH TIME ZONE,
  failure_reason VARCHAR(512),
  CONSTRAINT uq_attempt_number UNIQUE (tenant_id, payment_id, attempt_number)
);
CREATE INDEX idx_attempts_tenant_payment ON payment_attempts(tenant_id, payment_id);

CREATE TABLE idempotency_keys (
  id UUID PRIMARY KEY,
  tenant_id UUID NOT NULL,
  operation VARCHAR(64) NOT NULL,
  key VARCHAR(256) NOT NULL,
  request_hash VARCHAR(128) NOT NULL,
  response_status INTEGER,
  response_body TEXT,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
  CONSTRAINT uq_idempotency UNIQUE (tenant_id, operation, key)
);

CREATE TABLE invoices (
  id UUID PRIMARY KEY,
  tenant_id UUID NOT NULL,
  invoice_number VARCHAR(128) NOT NULL,
  payment_id UUID NOT NULL,
  order_id UUID NOT NULL,
  customer_id UUID,
  currency VARCHAR(3) NOT NULL,
  subtotal NUMERIC(19,4) NOT NULL CHECK (subtotal >= 0),
  total NUMERIC(19,4) NOT NULL CHECK (total >= 0),
  status VARCHAR(32) NOT NULL,
  issued_at TIMESTAMP WITH TIME ZONE,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
  CONSTRAINT uq_invoice_number UNIQUE (tenant_id, invoice_number),
  CONSTRAINT uq_invoice_payment UNIQUE (tenant_id, payment_id)
);
CREATE INDEX idx_invoices_tenant_order ON invoices(tenant_id, order_id);

CREATE TABLE refunds (
  id UUID PRIMARY KEY,
  tenant_id UUID NOT NULL,
  payment_id UUID NOT NULL,
  order_id UUID NOT NULL,
  refund_reference VARCHAR(128) NOT NULL,
  refund_amount NUMERIC(19,4) NOT NULL CHECK (refund_amount > 0),
  currency VARCHAR(3) NOT NULL,
  reason VARCHAR(512) NOT NULL,
  status VARCHAR(32) NOT NULL,
  provider_reference VARCHAR(256),
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
  processed_at TIMESTAMP WITH TIME ZONE,
  CONSTRAINT uq_refund_reference UNIQUE (tenant_id, refund_reference)
);
CREATE INDEX idx_refunds_tenant_payment ON refunds(tenant_id, payment_id);
