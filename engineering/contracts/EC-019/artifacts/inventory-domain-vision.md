# Inventory Domain Vision

Inventory garantiza que una orden confirmada pueda reservar stock de forma consistente, auditable y aislada por tenant.

## Capacidades

- consultar stock por producto y bodega;
- reservar, liberar y consumir stock;
- registrar ajustes autorizados;
- iniciar fulfillment y controlar picking/packing.

## Actores

Platform Administrator, Tenant Administrator, Warehouse Manager, Store Manager, Fulfillment Operator, Inventory Operator y Customer Service.

## Reglas

- nunca reservar mas que el stock disponible;
- toda operacion es tenant-aware;
- Order y Payment se consumen como eventos;
- no se recalculan precios ni montos;
- despacho y transporte quedan fuera.
