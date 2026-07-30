# Multi-Tenant Performance Review

- `stores.tenant_id` está indexado.
- `stores(tenant_id, slug)` tiene unicidad compuesta.
- `user_tenants(user_id, tenant_id)` tiene unicidad compuesta.
- Las consultas de Store se realizan por `tenant_id`.

Benchmark y profiling de producción quedan fuera de esta iteración.
