# Organization model

`Organization` se persiste por tenant en PostgreSQL mediante `organizations.tenant_id` único. Las propiedades comerciales se almacenan como JSONB y no se mezclan con datos de otro tenant.
