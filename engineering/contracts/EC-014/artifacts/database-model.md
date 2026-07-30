# Marketplace Database Model

La migración crea catalogs, categories y products reutilizando `stores` de EC-010B. Incluye FK, índices tenant-aware y unicidad `(tenant_id, sku)`.
