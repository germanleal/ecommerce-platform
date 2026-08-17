# Inventory Domain Model

## Inventory Aggregate

Representa el stock de un `sellableProductId` en una `warehouseLocationId`. Invariantes: `tenantId` obligatorio, cantidades no negativas, `available = onHand - reserved - committed` y ninguna reserva supera available.

## Reservation

Entidad con `tenantId`, `orderId`, `reservationReference`, lineas de producto, estado, expiracion e idempotency key. La reserva se crea desde un evento valido y se libera por cancelacion, expiracion o fallo de fulfillment.

## Fulfillment

Aggregate que referencia una Order y sus lineas reservadas. Coordina `ALLOCATED`, `PICKED` y `PACKED`; no contiene logica de transporte.
