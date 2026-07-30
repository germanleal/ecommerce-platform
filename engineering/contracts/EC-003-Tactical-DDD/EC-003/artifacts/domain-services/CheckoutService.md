# Domain Service: CheckoutService

## Responsabilidad
Orquestar la transición de un carrito de compras a un pedido en firme, validando la disponibilidad de stock y bloqueando las reservas necesarias.

## Justificación
Involucra la coordinación entre el contexto de **Store Operations** (para validar stock en `InventoryItem`) y el contexto de **Order Fulfillment** (para crear la `Order`). Ningún agregado puede realizar esta orquestación sin acoplarse excesivamente a otro contexto.

## Operaciones
### `placeOrder(customerId, storeId, items)`
- **Entradas**: ID del cliente, ID de la tienda, lista de pares (SKU, cantidad).
- **Salidas**: Objeto `Order` creado o error de validación.
- **Flujo Conceptual**:
    1. Solicita al `InventoryRepository` los items por SKU.
    2. Verifica que `available_quantity >= requested_quantity` para cada item.
    3. Ejecuta `reserveStock(sku, quantity)` en los agregados `InventoryItem`.
    4. Crea el agregado `Order` con el estado `CREATED`.
    5. Dispara el evento `OrderPlaced`.

## Restricciones
- Si una reserva de stock falla, se deben revertir todas las reservas anteriores (Atomicidad conceptual).
