# Domain Service: PricingEngine

## Responsabilidad
Calcular el precio final de un producto o pedido aplicando reglas de negocio complejas como promociones vigentes, impuestos por región y descuentos por volumen.

## Justificación
La lógica de precios puede volverse muy compleja y depender de factores externos al Agregado `Product` (ej. fecha actual, ubicación del cliente, cupones). Centralizar esta lógica evita duplicidad en los agregados de `Product` y `Order`.

## Operaciones
### `calculateProductPrice(product, customerContext)`
- **Entradas**: Agregado `Product`, Contexto del cliente (ubicación, tipo de cliente).
- **Salidas**: Objeto `Price` calculado.

## Restricciones
- El motor debe ser determinista: para las mismas entradas y el mismo estado del sistema, el precio debe ser idéntico.
