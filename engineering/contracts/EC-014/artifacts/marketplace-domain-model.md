# Marketplace Domain Model

```text
Tenant
  └── Store
        └── Catalog
              ├── Category
              └── Product
                    └── Variant
```

Cada raíz mantiene sus invariantes y se referencia mediante IDs entre agregados. Ningún agregado cruza límites mediante referencias mutables directas.

## Lifecycles

- Store: `CREATED → CONFIGURED → ACTIVE → SUSPENDED → CLOSED`.
- Product: `DRAFT → ACTIVE → INACTIVE → ARCHIVED`.
