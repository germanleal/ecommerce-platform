ALTER TABLE orders ADD COLUMN IF NOT EXISTS version BIGINT NOT NULL DEFAULT 0;
CREATE INDEX IF NOT EXISTS idx_orders_tenant_status ON orders(tenant_id,status);
CREATE INDEX IF NOT EXISTS idx_orders_tenant_id ON orders(tenant_id,id);
ALTER TABLE order_items DROP CONSTRAINT IF EXISTS order_items_order_id_fkey;
ALTER TABLE order_items ADD CONSTRAINT order_items_order_id_fkey FOREIGN KEY(order_id) REFERENCES orders(id) ON DELETE CASCADE;
