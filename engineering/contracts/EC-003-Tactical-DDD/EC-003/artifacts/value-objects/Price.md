# Value Object: Price

## Propósito
Representar el valor comercial de un producto incluyendo los componentes de impuestos y descuentos.

## Propiedades
- `baseAmount`: Objeto `Money` base.
- `taxPercentage`: Porcentaje de impuesto aplicable.
- `discountAmount`: Objeto `Money` de descuento aplicado.

## Reglas de Validación
- El monto final (`base + tax - discount`) no puede ser menor a cero.

## Inmutabilidad
- Cualquier cambio en las condiciones comerciales genera un nuevo objeto `Price`.

## Justificación
Es una descripción de valor compuesta que se adjunta a productos o líneas de pedido.
