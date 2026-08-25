# Entity: LineItem

## Propósito
Representar la captura de un producto, su cantidad y su precio en el momento exacto de la compra.

## Identidad
**LineItemId** (UUID generado internamente por la orden).

## Responsabilidad
Garantizar que el precio pactado con el cliente se mantenga inmutable aunque el producto cambie de precio en el catálogo posteriormente.

## Reglas Propias
- La cantidad debe ser un entero positivo mayor a cero.
- Debe almacenar el `SKU`, el nombre del producto y el precio unitario capturado.

## Restricciones
- No puede existir fuera del contexto de una `Order`.

## Relación con el Aggregate
Entidad interna del Agregado **Order**.

## Justificación
Es una Entity porque tiene un ciclo de vida dentro de la orden (puede ser modificada en cantidad antes de confirmarse) y necesita ser distinguida de otras líneas del mismo pedido.
