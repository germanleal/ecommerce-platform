# 16. Domain Specifications

## Objetivo
Definir reglas de validación complejas y reutilizables que determinan si un objeto o estado cumple con ciertos criterios de negocio.

## Catálogo de Specifications

### 1. ProductPublishableSpecification
- **Regla**: Un producto solo es publicable si tiene nombre, descripción, al menos una imagen y al menos una variante con stock > 0.
- **Contexto**: Store Operations.

### 2. OrderCancellableSpecification
- **Regla**: Una orden solo puede ser cancelada por el cliente si su estado es `AWAITING_PAYMENT` o `PAID` pero no ha iniciado `PREPARING`.
- **Contexto**: Order Fulfillment.

### 3. EligibleForPromotionSpecification
- **Regla**: Determina si un carrito de compras cumple las condiciones para aplicar un cupón específico (ej. monto mínimo, categoría de productos).
- **Contexto**: Promotions.
