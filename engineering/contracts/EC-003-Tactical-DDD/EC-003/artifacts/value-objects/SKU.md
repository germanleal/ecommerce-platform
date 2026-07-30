# Value Object: SKU

## Propósito
Representar el código único de inventario.

## Propiedades
- `code`: Cadena de texto alfanumérica.

## Reglas de Validación
- Debe cumplir con un formato estándar (ej. longitud mínima, sin caracteres especiales prohibidos).

## Justificación
Aunque identifica una entidad, el código en sí es un valor inmutable. Si el código cambia, es un SKU diferente.
