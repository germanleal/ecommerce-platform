-- Idempotent MVP demonstration dataset. Runs after service Flyway migrations.
BEGIN;
INSERT INTO public.organizations (id,name,slug,status,created_at,updated_at)
VALUES ('10000000-0000-0000-0000-000000000001','Demo Organization','demo-organization','ACTIVE',now(),now())
ON CONFLICT (id) DO UPDATE SET name=EXCLUDED.name, status='ACTIVE', updated_at=now();
INSERT INTO public.tenants (id,organization_id,name,slug,status,configuration,created_at,updated_at)
VALUES ('10000000-0000-0000-0000-000000000002','10000000-0000-0000-0000-000000000001','Demo Tenant','demo-tenant','ACTIVE','{}',now(),now())
ON CONFLICT (id) DO UPDATE SET name=EXCLUDED.name,status='ACTIVE',updated_at=now();
INSERT INTO public.stores (id,tenant_id,name,slug,status,configuration,created_at,updated_at)
VALUES ('10000000-0000-0000-0000-000000000003','10000000-0000-0000-0000-000000000002','Demo Store','demo-store','ACTIVE','{}',now(),now())
ON CONFLICT (id) DO UPDATE SET name=EXCLUDED.name,status='ACTIVE',updated_at=now();
INSERT INTO marketplace.catalogs (id,tenant_id,store_id,name,status)
VALUES ('10000000-0000-0000-0000-000000000004','10000000-0000-0000-0000-000000000002','10000000-0000-0000-0000-000000000003','Demo Catalog','ACTIVE') ON CONFLICT (id) DO NOTHING;
INSERT INTO marketplace.categories (id,tenant_id,catalog_id,name,description,status)
VALUES ('10000000-0000-0000-0000-000000000005','10000000-0000-0000-0000-000000000002','10000000-0000-0000-0000-000000000004','Demo Products','Products for the MVP demonstration','ACTIVE') ON CONFLICT (id) DO NOTHING;
INSERT INTO marketplace.products (id,tenant_id,store_id,catalog_id,category_id,sku,name,description,status)
VALUES ('10000000-0000-0000-0000-000000000006','10000000-0000-0000-0000-000000000002','10000000-0000-0000-0000-000000000003','10000000-0000-0000-0000-000000000004','10000000-0000-0000-0000-000000000005','DEMO-001','Demo Product','Ready-to-buy MVP demonstration product','ACTIVE') ON CONFLICT (id) DO NOTHING;
INSERT INTO commerce.sellable_products (id,tenant_id,marketplace_product_id,store_id,status,created_at,updated_at)
VALUES ('10000000-0000-0000-0000-000000000007','10000000-0000-0000-0000-000000000002','10000000-0000-0000-0000-000000000006','10000000-0000-0000-0000-000000000003','ENABLED',now(),now()) ON CONFLICT (id) DO UPDATE SET status='ENABLED',updated_at=now();
INSERT INTO commerce.prices (id,tenant_id,sellable_product_id,amount,currency,valid_from,valid_until,status,created_at,updated_at)
VALUES ('10000000-0000-0000-0000-000000000008','10000000-0000-0000-0000-000000000002','10000000-0000-0000-0000-000000000007',19.99,'USD','2020-01-01','2099-01-01','ACTIVE',now(),now()) ON CONFLICT (id) DO UPDATE SET amount=EXCLUDED.amount,status='ACTIVE',updated_at=now();
COMMIT;
