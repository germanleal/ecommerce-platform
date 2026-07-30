# Tenant Isolation

Las consultas multi-tenant deben filtrar por `tenant_id`, usar constraints e índices adecuados y nunca confiar en un identificador entregado por el frontend.
