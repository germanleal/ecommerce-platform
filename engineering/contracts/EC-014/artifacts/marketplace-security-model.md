# Marketplace Security Model

Permisos iniciales:

- `STORE_CREATE`, `STORE_UPDATE`.
- `PRODUCT_CREATE`, `PRODUCT_UPDATE`, `PRODUCT_DELETE`.

La autorización combina identidad EC-010A, tenant context EC-010B, rol, permiso y recurso. Ningún frontend define el tenant autorizado.
