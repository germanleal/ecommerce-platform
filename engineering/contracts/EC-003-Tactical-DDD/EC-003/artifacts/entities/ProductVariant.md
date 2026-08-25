# Entity: ProductVariant

## Propósito
Representar una combinación específica de atributos de un producto que puede ser vendida (ej. Camiseta Roja Talla L).

## Identidad
**SKU (Stock Keeping Unit)**. Es el identificador único que vincula el catálogo con el inventario físico.

## Responsabilidad
Mantener los atributos específicos (color, talla, material), el precio específico de la variante y su estado de disponibilidad comercial.

## Reglas Propias
- Una variante debe tener un SKU único dentro de la tienda.
- Si no se especifica un precio para la variante, hereda el precio base del `Product`.

## Restricciones
- No puede existir sin un `Product` padre.
- Los atributos de la variante deben ser consistentes con la definición del producto.

## Relación con el Aggregate
Es una entidad interna del Agregado **Product**. La raíz del agregado orquestra los cambios en sus variantes.

## Justificación
Es una Entity porque aunque sus atributos sean iguales a otra, su identidad (SKU) es lo que permite el seguimiento individual en inventario y ventas.
