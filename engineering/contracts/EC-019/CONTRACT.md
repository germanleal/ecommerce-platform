# Engineering Contract: EC-019 - Inventory & Fulfillment

## Parte 1/4 - Inventory Domain Discovery & Fulfillment Domain Design

Esta parte define el dominio y no implementa codigo, APIs ni persistencia. Inventory consume eventos publicos de Order y Payments, mantiene `tenantId` en sus entidades y no modifica otros contextos.

Estado: `READY FOR INVENTORY IMPLEMENTATION`

## Bounded contexts

- Inventory: stock fisico, disponible, reservado, comprometido y ajustes.
- Warehouse: bodegas, ubicaciones y asignacion de stock.
- Fulfillment: reserva operacional, picking y packing. No incluye despacho ni tracking.

## Fuera de alcance

Marketplace, Commerce, Order, Payments, transportistas, rutas, despacho, tracking, ERP, WMS externo, IoT y RFID.

## Entregable de Parte 2

Implementar Inventory, Warehouse, Reservation, persistencia PostgreSQL/Flyway, APIs REST, Kafka y gestion de stock.
