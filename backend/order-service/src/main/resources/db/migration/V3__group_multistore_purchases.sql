ALTER TABLE orders ADD COLUMN purchase_id UUID;
UPDATE orders SET purchase_id = id WHERE purchase_id IS NULL;
ALTER TABLE orders ALTER COLUMN purchase_id SET NOT NULL;
CREATE INDEX idx_orders_purchase ON orders(purchase_id);
