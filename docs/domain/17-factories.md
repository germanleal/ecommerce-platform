# 17. Factories

## Objetivo
Definir los mecanismos de creación de agregados complejos, asegurando que nazcan en un estado válido y consistente.

## Catálogo de Factories

### 1. TenantFactory
- **Objeto**: `Tenant` Aggregate.
- **Cuándo utilizarse**: Durante el proceso de Onboarding.
- **Responsabilidad**: Crear el agregado `Tenant` con sus valores iniciales, generar el `tenant_id` y asegurar que el administrador inicial esté correctamente vinculado.

### 2. OrderFactory
- **Objeto**: `Order` Aggregate.
- **Cuándo utilizarse**: Al finalizar exitosamente un Checkout.
- **Responsabilidad**: Transformar el estado actual del Carrito en una Orden inmutable, capturando precios y aplicando descuentos finales.

