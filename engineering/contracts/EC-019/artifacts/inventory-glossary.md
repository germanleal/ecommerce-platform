# Inventory Glossary

| Termino | Definicion | Regla principal |
| --- | --- | --- |
| Inventory | Existencias de un producto en una bodega | Pertenece a un tenant y una ubicacion |
| Stock | Cantidad fisica registrada | No puede ser negativa |
| Available Quantity | Stock no reservado ni comprometido | Es el limite para nuevas reservas |
| Reserved Quantity | Stock apartado para una orden | Se libera o consume |
| Warehouse | Bodega del tenant | Puede contener multiples ubicaciones |
| Reservation | Compromiso temporal de stock | Tiene expiracion e idempotencia |
| Fulfillment | Preparacion operacional de una orden | Incluye picking y packing, no despacho |
| Picking | Recoleccion de unidades | Consume una reserva asignada |
| Packing | Empaque preparado para entrega | No implica transporte |
| Stock Adjustment | Cambio autorizado de existencia | Requiere motivo y auditoria |
| Warehouse Location | Ubicacion dentro de una bodega | Es unica dentro del tenant y bodega |
