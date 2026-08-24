-- OPTIONAL demo/dummy dataset. Never runs by default: only via `docker compose --profile demo up postgres-seed`.
-- Do not rely on this data for real tenants; it exists solely to exercise the MVP demo flow end to end.
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

INSERT INTO administration.administration_companies(id,tenant_id,legal_name,commercial_name,tax_id,description,email,phone,website,address,city,country,status,created_at,updated_at)
VALUES('23000000-0000-0000-0000-000000000001','10000000-0000-0000-0000-000000000002','Demo Company LLC','Demo Company','DEMO-TAX-001','MVP demo company','admin@demo.local','+100000000','https://demo.local','Demo Street 1','Demo City','US','ACTIVE',now(),now()) ON CONFLICT(id) DO UPDATE SET status='ACTIVE',updated_at=now();
INSERT INTO administration.administration_categories(id,tenant_id,name,description,status,created_at,updated_at) VALUES
('23000000-0000-0000-0000-000000000010','10000000-0000-0000-0000-000000000002','Electrónica','Electronic devices','ACTIVE',now(),now()),
('23000000-0000-0000-0000-000000000011','10000000-0000-0000-0000-000000000002','Computación','Computing products','ACTIVE',now(),now()),
('23000000-0000-0000-0000-000000000012','10000000-0000-0000-0000-000000000002','Accesorios','Computer accessories','ACTIVE',now(),now()) ON CONFLICT(id) DO NOTHING;
INSERT INTO administration.administration_taxes(id,tenant_id,code,name,rate,status,created_at,updated_at)
VALUES('23000000-0000-0000-0000-000000000020','10000000-0000-0000-0000-000000000002','IVA','IVA',19.0000,'ACTIVE',now(),now()) ON CONFLICT(id) DO UPDATE SET rate=19,status='ACTIVE',updated_at=now();
INSERT INTO administration.administration_products(id,tenant_id,company_id,category_id,tax_id,sku,name,description,short_description,brand,unit_of_measure,amount,currency,final_price,status,created_at,updated_at) VALUES
('23000000-0000-0000-0000-000000000101','10000000-0000-0000-0000-000000000002','23000000-0000-0000-0000-000000000001','23000000-0000-0000-0000-000000000011','23000000-0000-0000-0000-000000000020','NOTEBOOK-001','Notebook','Portable demo computer','MVP notebook','Demo','UNIT',999.00,'USD',1188.81,'ACTIVE',now(),now()),
('23000000-0000-0000-0000-000000000102','10000000-0000-0000-0000-000000000002','23000000-0000-0000-0000-000000000001','23000000-0000-0000-0000-000000000011','23000000-0000-0000-0000-000000000020','MONITOR-001','Monitor','27 inch demo monitor','MVP monitor','Demo','UNIT',249.00,'USD',296.31,'ACTIVE',now(),now()),
('23000000-0000-0000-0000-000000000103','10000000-0000-0000-0000-000000000002','23000000-0000-0000-0000-000000000001','23000000-0000-0000-0000-000000000012','23000000-0000-0000-0000-000000000020','KEYBOARD-001','Teclado','Mechanical demo keyboard','MVP keyboard','Demo','UNIT',79.00,'USD',94.01,'ACTIVE',now(),now()),
('23000000-0000-0000-0000-000000000104','10000000-0000-0000-0000-000000000002','23000000-0000-0000-0000-000000000001','23000000-0000-0000-0000-000000000012','23000000-0000-0000-0000-000000000020','MOUSE-001','Mouse','Wireless demo mouse','MVP mouse','Demo','UNIT',39.00,'USD',46.41,'ACTIVE',now(),now()),
('23000000-0000-0000-0000-000000000105','10000000-0000-0000-0000-000000000002','23000000-0000-0000-0000-000000000001','23000000-0000-0000-0000-000000000010','23000000-0000-0000-0000-000000000020','HEADSET-001','Audífonos','USB demo headset','MVP headset','Demo','UNIT',59.00,'USD',70.21,'ACTIVE',now(),now()) ON CONFLICT(id) DO UPDATE SET status='ACTIVE',updated_at=now();
INSERT INTO administration.administration_product_images(id,tenant_id,product_id,url,alt_text,sort_order,is_primary,status,created_at)
SELECT ('24000000-0000-0000-0000-'||lpad((n*2-1)::text,12,'0'))::uuid,'10000000-0000-0000-0000-000000000002',('23000000-0000-0000-0000-'||lpad((100+n)::text,12,'0'))::uuid,'https://picsum.photos/seed/product'||n||'/640/480','Demo product '||n,0,true,'ACTIVE',now() FROM generate_series(1,5)n ON CONFLICT(id) DO NOTHING;
INSERT INTO administration.administration_product_images(id,tenant_id,product_id,url,alt_text,sort_order,is_primary,status,created_at)
SELECT ('24000000-0000-0000-0000-'||lpad((n*2)::text,12,'0'))::uuid,'10000000-0000-0000-0000-000000000002',('23000000-0000-0000-0000-'||lpad((100+n)::text,12,'0'))::uuid,'https://picsum.photos/seed/product-detail'||n||'/640/480','Demo product detail '||n,1,false,'ACTIVE',now() FROM generate_series(1,5)n ON CONFLICT(id) DO NOTHING;
INSERT INTO administration.administration_services(id,tenant_id,company_id,tax_id,code,name,description,amount,currency,final_price,status,created_at,updated_at) VALUES
('23000000-0000-0000-0000-000000000201','10000000-0000-0000-0000-000000000002','23000000-0000-0000-0000-000000000001','23000000-0000-0000-0000-000000000020','INSTALL','Instalación','Product installation',50,'USD',59.50,'ACTIVE',now(),now()),
('23000000-0000-0000-0000-000000000202','10000000-0000-0000-0000-000000000002','23000000-0000-0000-0000-000000000001','23000000-0000-0000-0000-000000000020','SUPPORT','Soporte Técnico','Technical support',75,'USD',89.25,'ACTIVE',now(),now()) ON CONFLICT(id) DO UPDATE SET status='ACTIVE',updated_at=now();
INSERT INTO administration.administration_tenant_capabilities(tenant_id,capability_id,status,created_at,updated_at)
SELECT '10000000-0000-0000-0000-000000000002',id,'ACTIVE',now(),now() FROM administration.administration_capabilities ON CONFLICT(tenant_id,capability_id) DO UPDATE SET status='ACTIVE',updated_at=now();
INSERT INTO inventory.warehouses(id,tenant_id,code,name,active,created_at,description,status) VALUES('25000000-0000-0000-0000-000000000001','10000000-0000-0000-0000-000000000002','DEMO','Demo Warehouse',true,now(),'MVP demo warehouse','ACTIVE') ON CONFLICT(id) DO NOTHING;
INSERT INTO inventory.warehouse_locations(id,tenant_id,warehouse_id,code) VALUES('25000000-0000-0000-0000-000000000002','10000000-0000-0000-0000-000000000002','25000000-0000-0000-0000-000000000001','DEFAULT') ON CONFLICT(id) DO NOTHING;
INSERT INTO inventory.inventory(id,tenant_id,product_id,warehouse_location_id,on_hand,reserved,committed,updated_at,damaged,minimum_stock,maximum_stock)
SELECT ('25000000-0000-0000-0000-'||lpad((100+n)::text,12,'0'))::uuid,'10000000-0000-0000-0000-000000000002',('23000000-0000-0000-0000-'||lpad((100+n)::text,12,'0'))::uuid,'25000000-0000-0000-0000-000000000002',100,0,0,now(),0,5,200 FROM generate_series(1,5)n ON CONFLICT(id) DO UPDATE SET on_hand=GREATEST(inventory.inventory.on_hand,100),updated_at=now();

-- Stable UUIDs derived from tenant/resource keys make the expanded dataset idempotent.
CREATE OR REPLACE FUNCTION pg_temp.seed_uuid(value text) RETURNS uuid LANGUAGE SQL IMMUTABLE AS $$
 SELECT (substr(md5(value),1,8)||'-'||substr(md5(value),9,4)||'-4'||substr(md5(value),14,3)||'-a'||substr(md5(value),18,3)||'-'||substr(md5(value),21,12))::uuid
$$;

-- These two tenants match the tenant claims of tenant-user and tenant-b-user in the local Keycloak realm.
INSERT INTO public.organizations(id,name,slug,status,created_at,updated_at) VALUES
('00000000-0000-0000-0000-000000000100','Seed Commerce Organization','seed-commerce','ACTIVE',now(),now())
ON CONFLICT(id) DO UPDATE SET status='ACTIVE',updated_at=now();
INSERT INTO public.tenants(id,organization_id,name,slug,status,configuration,created_at,updated_at) VALUES
('00000000-0000-0000-0000-000000000001','00000000-0000-0000-0000-000000000100','Commerce Tenant A','commerce-tenant-a','ACTIVE','{}',now(),now()),
('00000000-0000-0000-0000-000000000002','00000000-0000-0000-0000-000000000100','Commerce Tenant B','commerce-tenant-b','ACTIVE','{}',now(),now())
ON CONFLICT(id) DO UPDATE SET name=EXCLUDED.name,status='ACTIVE',updated_at=now();

-- Give every active tenant two companies, two categories, one tax and four products.
INSERT INTO administration.administration_companies(id,tenant_id,legal_name,commercial_name,tax_id,description,email,phone,website,address,city,country,status,created_at,updated_at)
SELECT pg_temp.seed_uuid(t.id||':company:'||v.n),t.id,t.name||' '||v.suffix||' SpA',t.name||' '||v.suffix,
 'SEED-'||substr(replace(t.id::text,'-',''),1,8)||'-'||v.n,'Seed company for '||t.name,
 lower(replace(t.slug,'-',''))||v.n||'@seed.local','+56 2 2000 000'||v.n,'https://seed.local/'||t.slug,
 'Seed Avenue '||v.n,'Santiago','CL','ACTIVE',now(),now()
FROM public.tenants t CROSS JOIN (VALUES(1,'Market'),(2,'Home')) v(n,suffix) WHERE t.status='ACTIVE'
ON CONFLICT(id) DO UPDATE SET legal_name=EXCLUDED.legal_name,commercial_name=EXCLUDED.commercial_name,status='ACTIVE',updated_at=now();

INSERT INTO administration.administration_categories(id,tenant_id,name,description,status,created_at,updated_at)
SELECT pg_temp.seed_uuid(t.id||':category:'||v.code),t.id,v.name,v.description,'ACTIVE',now(),now()
FROM public.tenants t CROSS JOIN (VALUES('TECH','Technology','Computers and electronic accessories'),('HOME','Home & Office','Products for home and office')) v(code,name,description)
WHERE t.status='ACTIVE' ON CONFLICT(id) DO UPDATE SET status='ACTIVE',updated_at=now();

INSERT INTO administration.administration_taxes(id,tenant_id,code,name,rate,status,created_at,updated_at)
SELECT pg_temp.seed_uuid(t.id||':tax:IVA'),t.id,'IVA','IVA 19%',19.0000,'ACTIVE',now(),now()
FROM public.tenants t WHERE t.status='ACTIVE' ON CONFLICT(tenant_id,code) DO UPDATE SET rate=19,status='ACTIVE',updated_at=now();

INSERT INTO administration.administration_products(id,tenant_id,company_id,category_id,tax_id,sku,name,description,short_description,brand,unit_of_measure,amount,currency,final_price,status,created_at,updated_at)
SELECT pg_temp.seed_uuid(t.id||':product:'||v.code),t.id,pg_temp.seed_uuid(t.id||':company:'||v.company),
 pg_temp.seed_uuid(t.id||':category:'||v.category),(SELECT tx.id FROM administration.administration_taxes tx WHERE tx.tenant_id=t.id AND tx.code='IVA'),v.code,v.name,v.description,v.name,v.brand,'UNIT',v.amount,'USD',round(v.amount*1.19,2),'ACTIVE',now(),now()
FROM public.tenants t CROSS JOIN (VALUES
 (1,'TECH','SEED-LAPTOP','Laptop Pro','Portable computer for daily work','SeedTech',799.00::numeric),
 (1,'TECH','SEED-HEADPHONES','Wireless Headphones','Noise cancelling wireless headphones','SeedAudio',89.00::numeric),
 (2,'HOME','SEED-CHAIR','Ergonomic Chair','Adjustable chair for home and office','SeedHome',189.00::numeric),
 (2,'HOME','SEED-LAMP','Desk Lamp','LED desk lamp with adjustable brightness','SeedHome',39.00::numeric)
) v(company,category,code,name,description,brand,amount)
WHERE t.status='ACTIVE' ON CONFLICT(id) DO UPDATE SET company_id=EXCLUDED.company_id,category_id=EXCLUDED.category_id,tax_id=EXCLUDED.tax_id,name=EXCLUDED.name,amount=EXCLUDED.amount,final_price=EXCLUDED.final_price,status='ACTIVE',deleted_at=NULL,updated_at=now();

INSERT INTO administration.administration_product_images(id,tenant_id,product_id,url,alt_text,sort_order,is_primary,status,created_at)
SELECT pg_temp.seed_uuid(ap.id||':image:primary'),ap.tenant_id,ap.id,'https://picsum.photos/seed/'||replace(ap.id::text,'-','')||'/640/480',ap.name,0,true,'ACTIVE',now()
FROM administration.administration_products ap WHERE ap.status='ACTIVE' AND ap.deleted_at IS NULL
AND NOT EXISTS (SELECT 1 FROM administration.administration_product_images current_image WHERE current_image.product_id=ap.id AND current_image.is_primary=true)
ON CONFLICT(id) DO UPDATE SET url=EXCLUDED.url,alt_text=EXCLUDED.alt_text,status='ACTIVE';

INSERT INTO administration.administration_tenant_capabilities(tenant_id,capability_id,status,created_at,updated_at)
SELECT t.id,c.id,'ACTIVE',now(),now() FROM public.tenants t CROSS JOIN administration.administration_capabilities c WHERE t.status='ACTIVE' AND c.status='ACTIVE'
ON CONFLICT(tenant_id,capability_id) DO UPDATE SET status='ACTIVE',updated_at=now();

-- Mirror active administration products into Commerce using the same product UUID.
-- This lets cart/order-service resolve the product and authoritative gross price during purchase-order creation.
INSERT INTO commerce.commerce_products(id,tenant_id,sku,name,description,status,created_at,updated_at)
SELECT ap.id,ap.tenant_id,ap.sku,ap.name,ap.description,'ACTIVE',now(),now()
FROM administration.administration_products ap JOIN administration.administration_companies c ON c.id=ap.company_id AND c.status='ACTIVE'
WHERE ap.status='ACTIVE' AND ap.deleted_at IS NULL
ON CONFLICT(id) DO UPDATE SET name=EXCLUDED.name,description=EXCLUDED.description,status='ACTIVE',updated_at=now();

INSERT INTO commerce.sellable_products(id,tenant_id,marketplace_product_id,store_id,status,created_at,updated_at)
SELECT ap.id,ap.tenant_id,ap.id,ap.company_id,'ENABLED',now(),now()
FROM administration.administration_products ap JOIN administration.administration_companies c ON c.id=ap.company_id AND c.status='ACTIVE'
WHERE ap.status='ACTIVE' AND ap.deleted_at IS NULL
ON CONFLICT(id) DO UPDATE SET store_id=EXCLUDED.store_id,status='ENABLED',updated_at=now();

INSERT INTO commerce.prices(id,tenant_id,sellable_product_id,amount,currency,valid_from,valid_until,status,created_at,updated_at)
SELECT pg_temp.seed_uuid(ap.id||':price'),ap.tenant_id,ap.id,ap.final_price,ap.currency,'2020-01-01','2099-01-01','ACTIVE',now(),now()
FROM administration.administration_products ap JOIN administration.administration_companies c ON c.id=ap.company_id AND c.status='ACTIVE'
WHERE ap.status='ACTIVE' AND ap.deleted_at IS NULL
ON CONFLICT(id) DO UPDATE SET amount=EXCLUDED.amount,currency=EXCLUDED.currency,status='ACTIVE',updated_at=now();

INSERT INTO inventory.warehouses(id,tenant_id,code,name,active,created_at,description,status)
SELECT pg_temp.seed_uuid(t.id||':warehouse'),t.id,'SEED','Seed Warehouse',true,now(),'Default seeded warehouse','ACTIVE'
FROM public.tenants t WHERE t.status='ACTIVE' ON CONFLICT(id) DO UPDATE SET active=true,status='ACTIVE';
INSERT INTO inventory.warehouse_locations(id,tenant_id,warehouse_id,code)
SELECT pg_temp.seed_uuid(t.id||':location'),t.id,pg_temp.seed_uuid(t.id||':warehouse'),'DEFAULT'
FROM public.tenants t WHERE t.status='ACTIVE' ON CONFLICT(id) DO NOTHING;
INSERT INTO inventory.inventory(id,tenant_id,product_id,warehouse_location_id,on_hand,reserved,committed,updated_at,damaged,minimum_stock,maximum_stock)
SELECT pg_temp.seed_uuid(ap.id||':inventory'),ap.tenant_id,ap.id,pg_temp.seed_uuid(ap.tenant_id||':location'),100,0,0,now(),0,5,200
FROM administration.administration_products ap WHERE ap.status='ACTIVE' AND ap.deleted_at IS NULL
ON CONFLICT(id) DO UPDATE SET on_hand=GREATEST(inventory.inventory.on_hand,100),updated_at=now();
COMMIT;
