# Commerce Domain Model

```text
Tenant
  └── Store
        └── Sellable Product
              └── Offer
                    └── Price
```

Commerce consume la identidad del producto publicado desde Marketplace, pero mantiene su propia decisión de vendibilidad. Todo agregado posee `tenantId`.
