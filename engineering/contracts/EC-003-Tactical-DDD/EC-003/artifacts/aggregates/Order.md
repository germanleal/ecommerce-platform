# Aggregate Root: Order

## 1. Identificación
- **Nombre**: Order
- **Contexto**: Order Fulfillment
- **Objetivo**: Representar el contrato transaccional de una compra.
- **Responsabilidad principal**: Orquestar el ciclo de vida del pedido, desde la creación hasta la entrega final, garantizando la consistencia de los montos y estados.

## 2. Límites
- **Qué pertenece**: Line items, dirección de envío, totales, historial de estados del pedido.
- **Qué NO pertenece**: Procesamiento de pago (BC Payments), gestión de stock (BC Inventory).
- **Qué reglas protege**: Integridad de montos, validez de transiciones de estado.
- **Qué datos controla**: `order_id`, `customer_id`, `store_id`, `tenant_id`, `items`, `total_amount`, `status`, `shipping_address`.
- **Qué comportamiento encapsula**: Colocación del pedido, pago, despacho, cancelación.

## 3. Invariantes
- **BR-ORD-01**: Una orden debe contener al menos un `LineItem`.
- **BR-ORD-02**: El `total_amount` debe ser la suma exacta de los precios de los items + envío + impuestos.
- **BR-ORD-03**: No se puede transicionar a `SHIPPED` si el pedido no está en estado `PAID`.
- **BR-ORD-04**: El precio de los items se congela al momento de crear la orden (inmutabilidad de precio de venta).

## 4. Ciclo de Vida
### Estados
- **CREATED**: Pedido iniciado pero aún no confirmado por el cliente.
- **AWAITING_PAYMENT**: Confirmado, esperando respuesta de la pasarela de pagos.
- **PAID**: Pago confirmado, listo para ser procesado por la tienda.
- **PREPARING**: La tienda está empaquetando los productos.
- **SHIPPED**: El pedido ha sido entregado al transportista.
- **DELIVERED**: El cliente ha recibido el producto.
- **CANCELLED**: El pedido ha sido anulado.

### Transiciones
- `CREATED` -> `AWAITING_PAYMENT`: Tras confirmar el carrito.
- `AWAITING_PAYMENT` -> `PAID`: Al recibir evento de pago aprobado.
- `PAID` -> `PREPARING`: Acción manual del `Store Manager`.
- `PREPARING` -> `SHIPPED`: Al generar la guía de despacho.
- `*` -> `CANCELLED`: Según políticas de cancelación.

## 5. Responsabilidades
- **Qué hace**: Mantiene la verdad sobre lo que el cliente compró y en qué estado se encuentra su entrega.
- **Qué NO hace**: No realiza el cobro bancario ni descuenta físicamente el stock (reacciona a eventos o solicita servicios).

## 6. Relaciones
- Contiene una colección de `LineItem` (Entity).
- Usa `Address` (Value Object) para el envío.
