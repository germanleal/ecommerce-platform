# Inventory Security Model

Permisos iniciales:

- `INVENTORY_READ`;
- `INVENTORY_UPDATE`;
- `INVENTORY_RESERVE`;
- `INVENTORY_RELEASE`;
- `WAREHOUSE_MANAGE`;
- `FULFILLMENT_EXECUTE`.

Todas las entidades llevan `tenantId`. El tenant se obtiene del JWT/TenantContext y no se acepta como autoridad desde el body. Warehouse Manager administra bodegas; Inventory Operator ajusta stock; Fulfillment Operator ejecuta picking y packing.
