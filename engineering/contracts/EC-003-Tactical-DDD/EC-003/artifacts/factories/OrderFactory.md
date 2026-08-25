# Factory: OrderFactory

## Objeto Construido
Agregado **Order**.

## Razón de Existencia
La creación de una orden requiere validar la integridad de múltiples line items, calcular el precio total usando el `PricingEngine` y asegurar que todos los datos de envío sean válidos. Es un proceso de construcción complejo que no debería residir en el constructor de la entidad.

## Restricciones
- No puede crear una orden sin un `customerId` válido y al menos un item.

## Validaciones
- Valida que todos los items pertenezcan a la misma tienda (Aislamiento de tienda).
- Valida la consistencia de los precios capturados.

## Casos de Uso
- Checkout del marketplace.
