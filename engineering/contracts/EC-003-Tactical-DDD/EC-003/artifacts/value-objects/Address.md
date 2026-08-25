# Value Object: Address

## Propósito
Representar una ubicación geográfica estructurada para envíos y facturación.

## Propiedades
- `street`
- `city`
- `state`
- `zipCode`
- `country`

## Restricciones
- Todos los campos son obligatorios (según la región).

## Inmutabilidad
- Si la dirección cambia, se debe reemplazar el objeto completo.

## Igualdad por Valor
- Dos direcciones son iguales si todos sus campos coinciden exactamente.

## Justificación
No necesitamos rastrear la identidad de una dirección; solo nos interesa la información que contiene para procesar el pedido.
