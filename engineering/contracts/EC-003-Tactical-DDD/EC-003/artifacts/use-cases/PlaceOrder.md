# Use Case: PlaceOrder

## Objetivo
Permitir a un cliente comprar productos de una tienda específica, transformando su carrito en un pedido en firme.

## Actores
- **Customer** (Principal)
- **Inventory System** (Secundario - Validación)
- **Payment Gateway** (Secundario - Disparador posterior)

## Precondiciones
- El cliente está autenticado.
- La tienda está en estado `PUBLISHED`.
- El carrito contiene al menos un producto con stock disponible.

## Flujo Principal
1. El cliente inicia el proceso de checkout.
2. El sistema solicita al `PricingEngine` el cálculo final de precios (incluyendo impuestos y envío).
3. El sistema utiliza el `CheckoutService` para:
    a. Validar y reservar el stock de cada item en el BC de Inventario.
    b. Crear la instancia de `Order` usando la `OrderFactory`.
4. El sistema persiste la orden a través del `OrderRepository`.
5. El sistema emite el evento `OrderPlaced`.

## Flujos Alternativos
- **Sin Stock**: Si algún item no tiene stock suficiente, el sistema cancela la operación y notifica al cliente indicando los productos afectados.
- **Tienda no activa**: Si la tienda fue suspendida durante el proceso, se rechaza la creación del pedido.

## Postcondiciones
- Existe un agregado `Order` en estado `CREATED`.
- El stock de los productos está en estado `RESERVED`.

## Reglas de Negocio
- **BR-ORD-01**, **BR-ORD-02**, **BR-ORD-04**.

## Eventos Disparados
- `OrderPlaced`.
