# Entity: StoreInstance

## Propósito
Registro administrativo de una tienda dentro del contexto de gestión de una empresa (Tenant).

## Identidad
**StoreId** (vinculado al ID de la tienda en el BC de Store Operations).

## Responsabilidad
Mapear la pertenencia de una tienda a un Tenant y controlar su estado administrativo global.

## Relación con el Aggregate
Entidad interna del Agregado **Tenant**.

## Justificación
Es una Entity porque representa la existencia de una unidad operativa vinculada a la organización, con su propio ciclo de vida administrativo.
