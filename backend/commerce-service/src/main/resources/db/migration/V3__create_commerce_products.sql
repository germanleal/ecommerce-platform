CREATE TABLE commerce_products (id UUID PRIMARY KEY, tenant_id UUID NOT NULL, sku VARCHAR(64) NOT NULL, name VARCHAR(255) NOT NULL, description TEXT, status VARCHAR(32) NOT NULL, created_at TIMESTAMPTZ NOT NULL, updated_at TIMESTAMPTZ NOT NULL, CONSTRAINT uq_commerce_product_tenant_sku UNIQUE (tenant_id,sku));
CREATE INDEX idx_commerce_products_tenant ON commerce_products(tenant_id);
CREATE INDEX idx_commerce_products_status ON commerce_products(tenant_id,status);
