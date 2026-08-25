# Entity: Category

## Propósito
Organizar los productos de una tienda en una estructura jerárquica lógica.

## Identidad
**CategoryId** (UUID).

## Responsabilidad
Gestionar el nombre, la descripción y la relación jerárquica (padre/hijo) de la taxonomía.

## Reglas Propias
- No se permiten ciclos en la jerarquía (una categoría no puede ser padre de su propio ancestro).

## Restricciones
- Pertenece a una única `Store`.

## Relación con el Aggregate
Entidad interna (o agregada según volumen) del contexto de **Store Operations**.

## Justificación
Es una Entity porque su nombre o posición en la jerarquía puede cambiar manteniendo la misma identidad para los productos vinculados.
