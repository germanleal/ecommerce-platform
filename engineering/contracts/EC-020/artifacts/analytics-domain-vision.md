# EC-020 Analytics & Reporting — Domain Vision

Analytics es un bounded context de lectura que transforma eventos públicos de Marketplace, Commerce, Order, Payments e Inventory en modelos analíticos por tenant.

## Capacidades

- Proyecciones de ventas, pagos, reembolsos, inventario y fulfillment.
- KPIs y tendencias con consistencia eventual.
- Reportes filtrables y exportables sin modificar fuentes transaccionales.
- Auditoría de origen, versión y momento de cada dato.

## Restricciones

Analytics no es autoridad de productos, precios, órdenes, pagos ni stock. No ejecuta comandos de negocio ni publica eventos de negocio.

## Resultado esperado

Un servicio desacoplado, replayable y tenant-aware, capaz de reconstruir sus read models desde eventos públicos versionados.
