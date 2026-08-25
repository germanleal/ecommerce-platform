# Value Object: Money

## Propósito
Representar una cantidad monetaria de forma segura y tipada, evitando errores de precisión decimal y mezcla de divisas.

## Propiedades
- `amount`: Cantidad numérica (precisión decimal alta).
- `currency`: Código ISO de la moneda (ej. USD, CLP).

## Restricciones
- El monto debe ser consistente con la escala de la moneda.

## Reglas de Validación
- No se permiten sumas o restas entre objetos `Money` con distintas `currency`.

## Inmutabilidad
- Toda operación aritmética devuelve una nueva instancia de `Money`.

## Igualdad por Valor
- Dos objetos `Money` son iguales si y solo si tienen el mismo `amount` y la misma `currency`.

## Justificación
No tiene identidad propia. $100 USD es intercambiable por cualquier otro $100 USD en el sistema. Su valor es lo que importa.
