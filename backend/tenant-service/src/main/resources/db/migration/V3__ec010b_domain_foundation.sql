ALTER TABLE organizations ADD COLUMN IF NOT EXISTS name VARCHAR(255);
ALTER TABLE organizations ADD COLUMN IF NOT EXISTS slug VARCHAR(120);
ALTER TABLE organizations ADD COLUMN IF NOT EXISTS status VARCHAR(32);
ALTER TABLE organizations ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ;
ALTER TABLE organizations ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ;

UPDATE organizations SET name = COALESCE(name, legal_name), slug = COALESCE(slug, 'organization-' || id::text), status = COALESCE(status, 'ACTIVE'), created_at = COALESCE(created_at, now()), updated_at = COALESCE(updated_at, now());
UPDATE tenants SET organization_id = id WHERE organization_id IS NULL;
INSERT INTO organizations(id, tenant_id, legal_name, commercial_properties, name, slug, status, created_at, updated_at)
SELECT t.organization_id, t.id, 'Organization ' || t.organization_id::text, '{}'::jsonb, 'Organization ' || t.organization_id::text, 'organization-' || t.organization_id::text, 'ACTIVE', now(), now()
FROM tenants t
WHERE NOT EXISTS (SELECT 1 FROM organizations o WHERE o.id = t.organization_id);
UPDATE tenants t SET organization_id = o.id
FROM organizations o WHERE o.tenant_id = t.id AND t.organization_id IS NULL;
ALTER TABLE organizations ALTER COLUMN name SET NOT NULL;
ALTER TABLE organizations ALTER COLUMN slug SET NOT NULL;
ALTER TABLE organizations ALTER COLUMN status SET NOT NULL;
ALTER TABLE organizations ALTER COLUMN created_at SET NOT NULL;
ALTER TABLE organizations ALTER COLUMN updated_at SET NOT NULL;
ALTER TABLE organizations DROP CONSTRAINT IF EXISTS organizations_tenant_id_key;
ALTER TABLE organizations DROP COLUMN IF EXISTS tenant_id;
ALTER TABLE organizations DROP COLUMN IF EXISTS legal_name;
ALTER TABLE organizations DROP COLUMN IF EXISTS commercial_properties;
ALTER TABLE organizations ADD CONSTRAINT uq_organizations_slug UNIQUE (slug);

ALTER TABLE tenants ADD CONSTRAINT fk_tenants_organization FOREIGN KEY (organization_id) REFERENCES organizations(id);
ALTER TABLE tenants ALTER COLUMN organization_id SET NOT NULL;
ALTER TABLE tenants ADD CONSTRAINT uq_tenant_slug_per_organization UNIQUE (organization_id, slug);
DROP INDEX IF EXISTS idx_tenants_slug;
CREATE INDEX IF NOT EXISTS idx_tenants_organization_id ON tenants(organization_id);
ALTER TABLE user_tenants ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ;
UPDATE user_tenants SET updated_at = created_at WHERE updated_at IS NULL;
ALTER TABLE user_tenants ALTER COLUMN updated_at SET NOT NULL;
CREATE INDEX IF NOT EXISTS idx_user_tenants_active ON user_tenants(user_id, tenant_id, status);
