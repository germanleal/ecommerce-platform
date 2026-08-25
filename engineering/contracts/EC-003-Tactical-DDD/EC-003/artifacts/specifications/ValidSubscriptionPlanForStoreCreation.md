# Domain Specification: ValidSubscriptionPlanForStoreCreation

## Regla Evaluada
Un Tenant solo puede crear una nueva tienda si su plan de suscripción actual permite más tiendas de las que ya tiene activas.

## Contexto
**Provisioning & Tenant Management**.

## Entradas
- Agregado `Tenant`.
- Lista de `StoreInstance` actuales.

## Resultado Esperado
Booleano (`true` si puede crear, `false` si excedió el límite).

## Casos de Uso
- Registro de nueva tienda.
- Upgrade de plan.
