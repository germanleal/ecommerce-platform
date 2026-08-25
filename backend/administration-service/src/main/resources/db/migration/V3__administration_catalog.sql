CREATE TABLE administration_companies (
 id UUID PRIMARY KEY, tenant_id UUID NOT NULL, legal_name VARCHAR(255) NOT NULL,
 commercial_name VARCHAR(255) NOT NULL, tax_id VARCHAR(80) NOT NULL, description TEXT,
 email VARCHAR(255), phone VARCHAR(80), website VARCHAR(512), address VARCHAR(512), city VARCHAR(128), country VARCHAR(2),
 status VARCHAR(16) NOT NULL CHECK(status IN ('ACTIVE','INACTIVE')), created_at TIMESTAMPTZ NOT NULL, updated_at TIMESTAMPTZ NOT NULL,
 UNIQUE(tenant_id,tax_id));
CREATE INDEX idx_admin_companies_tenant_status ON administration_companies(tenant_id,status);

CREATE TABLE administration_categories (
 id UUID PRIMARY KEY, tenant_id UUID NOT NULL, name VARCHAR(255) NOT NULL, description TEXT,
 status VARCHAR(16) NOT NULL CHECK(status IN ('ACTIVE','INACTIVE')), created_at TIMESTAMPTZ NOT NULL, updated_at TIMESTAMPTZ NOT NULL,
 UNIQUE(tenant_id,name));
CREATE INDEX idx_admin_categories_tenant_status ON administration_categories(tenant_id,status);

CREATE TABLE administration_taxes (
 id UUID PRIMARY KEY, tenant_id UUID NOT NULL, code VARCHAR(64) NOT NULL, name VARCHAR(255) NOT NULL,
 rate NUMERIC(7,4) NOT NULL CHECK(rate>=0 AND rate<=100), status VARCHAR(16) NOT NULL CHECK(status IN ('ACTIVE','INACTIVE')),
 created_at TIMESTAMPTZ NOT NULL, updated_at TIMESTAMPTZ NOT NULL, UNIQUE(tenant_id,code));
CREATE INDEX idx_admin_taxes_tenant_status ON administration_taxes(tenant_id,status);

CREATE TABLE administration_products (
 id UUID PRIMARY KEY, tenant_id UUID NOT NULL, company_id UUID NOT NULL REFERENCES administration_companies(id),
 category_id UUID NOT NULL REFERENCES administration_categories(id), tax_id UUID REFERENCES administration_taxes(id),
 sku VARCHAR(80) NOT NULL, name VARCHAR(255) NOT NULL, description TEXT, short_description VARCHAR(512), brand VARCHAR(128),
 unit_of_measure VARCHAR(32) NOT NULL, amount NUMERIC(19,4) NOT NULL CHECK(amount>=0), currency VARCHAR(3) NOT NULL,
 final_price NUMERIC(19,4) NOT NULL CHECK(final_price>=0), status VARCHAR(16) NOT NULL CHECK(status IN ('DRAFT','ACTIVE','INACTIVE')),
 created_at TIMESTAMPTZ NOT NULL, updated_at TIMESTAMPTZ NOT NULL, deleted_at TIMESTAMPTZ, UNIQUE(tenant_id,sku));
CREATE INDEX idx_admin_products_tenant_status ON administration_products(tenant_id,status);
CREATE INDEX idx_admin_products_company ON administration_products(tenant_id,company_id);
CREATE INDEX idx_admin_products_category ON administration_products(tenant_id,category_id);

CREATE TABLE administration_product_images (
 id UUID PRIMARY KEY, tenant_id UUID NOT NULL, product_id UUID NOT NULL REFERENCES administration_products(id), url VARCHAR(2048) NOT NULL,
 alt_text VARCHAR(512), sort_order INT NOT NULL DEFAULT 0, is_primary BOOLEAN NOT NULL DEFAULT FALSE,
 status VARCHAR(16) NOT NULL CHECK(status IN ('ACTIVE','INACTIVE')), created_at TIMESTAMPTZ NOT NULL);
CREATE INDEX idx_admin_images_product ON administration_product_images(tenant_id,product_id,sort_order);
CREATE UNIQUE INDEX uq_admin_primary_image ON administration_product_images(product_id) WHERE is_primary=TRUE;

CREATE TABLE administration_services (
 id UUID PRIMARY KEY, tenant_id UUID NOT NULL, company_id UUID NOT NULL REFERENCES administration_companies(id), tax_id UUID REFERENCES administration_taxes(id),
 code VARCHAR(80) NOT NULL, name VARCHAR(255) NOT NULL, description TEXT, amount NUMERIC(19,4) NOT NULL CHECK(amount>=0),
 currency VARCHAR(3) NOT NULL, final_price NUMERIC(19,4) NOT NULL CHECK(final_price>=0),
 status VARCHAR(16) NOT NULL CHECK(status IN ('DRAFT','ACTIVE','INACTIVE')), created_at TIMESTAMPTZ NOT NULL, updated_at TIMESTAMPTZ NOT NULL,
 UNIQUE(tenant_id,code));
CREATE INDEX idx_admin_services_tenant_status ON administration_services(tenant_id,status);

CREATE TABLE administration_capabilities (
 id UUID PRIMARY KEY, code VARCHAR(80) NOT NULL UNIQUE, name VARCHAR(255) NOT NULL, description VARCHAR(512),
 status VARCHAR(16) NOT NULL CHECK(status IN ('ACTIVE','INACTIVE')));
CREATE TABLE administration_tenant_capabilities (
 tenant_id UUID NOT NULL, capability_id UUID NOT NULL REFERENCES administration_capabilities(id), status VARCHAR(16) NOT NULL CHECK(status IN ('ACTIVE','INACTIVE')),
 created_at TIMESTAMPTZ NOT NULL, updated_at TIMESTAMPTZ NOT NULL, PRIMARY KEY(tenant_id,capability_id));
CREATE INDEX idx_admin_tenant_capabilities ON administration_tenant_capabilities(tenant_id,status);

INSERT INTO administration_capabilities(id,code,name,description,status) VALUES
('22000000-0000-0000-0000-000000000001','MARKETPLACE','Marketplace','Marketplace storefront','ACTIVE'),
('22000000-0000-0000-0000-000000000002','PRODUCT_CATALOG','Product catalog','Product administration','ACTIVE'),
('22000000-0000-0000-0000-000000000003','SHOPPING_CART','Shopping cart','Cart operations','ACTIVE'),
('22000000-0000-0000-0000-000000000004','CHECKOUT','Checkout','Checkout operations','ACTIVE'),
('22000000-0000-0000-0000-000000000005','PAYMENTS','Payments','Payment processing','ACTIVE'),
('22000000-0000-0000-0000-000000000006','INVENTORY','Inventory','Inventory management','ACTIVE'),
('22000000-0000-0000-0000-000000000007','ORDERS','Orders','Order management','ACTIVE'),
('22000000-0000-0000-0000-000000000008','REPORTING','Reporting','Analytics and reporting','ACTIVE'),
('22000000-0000-0000-0000-000000000009','ADMINISTRATION','Administration','Administrative console','ACTIVE')
ON CONFLICT(code) DO NOTHING;
