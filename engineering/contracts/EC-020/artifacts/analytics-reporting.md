# Reporting Model

## Reportes mínimos

- **Ventas:** órdenes, unidades, monto neto, impuestos y moneda; filtros por período, tienda, canal y estado.
- **Facturación:** cobros, pagos fallidos, reembolsos y monto neto; filtros por período, método y moneda.
- **Productos:** unidades, ingresos y ranking; filtros por producto, categoría y período.
- **Clientes:** conteos y comportamiento agregado; nunca expone credenciales ni datos sensibles.
- **Inventario:** disponible, reservado, comprometido y ajustes; filtros por warehouse, producto y estado.
- **Fulfillment:** duración, estados, cancelaciones y preparación para despacho; filtros por warehouse y período.

Los reportes son read-only, tenant-scoped y soportan paginación. Exportación requiere `REPORT_EXPORT` y registra auditoría.
