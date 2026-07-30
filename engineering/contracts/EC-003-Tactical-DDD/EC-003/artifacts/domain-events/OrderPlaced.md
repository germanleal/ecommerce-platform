# Domain Event: OrderPlaced

## Nombre
`OrderPlaced`

## Aggregate Origen
**Order**.

## Contexto
**Order Fulfillment**.

## Actor
**Customer**.

## Causa
El cliente ha confirmado su intención de compra y el sistema ha validado la disponibilidad inicial de los productos.

## Consecuencia
1. El contexto de **Inventory** debe confirmar la reserva definitiva del stock.
2. El contexto de **Payments** debe iniciar la solicitud de cobro.
3. El cliente debe recibir una notificación de confirmación de recepción de pedido.

## Invariantes Relacionadas
- **BR-ORD-04**: Los precios en el evento deben ser los capturados al momento de la creación, no los actuales del catálogo.

## Información Mínima
- `order_id`
- `tenant_id`
- `store_id`
- `customer_id`
- `items` (SKU, cantidad, precio_unitario)
- `total_amount`
- `timestamp`
