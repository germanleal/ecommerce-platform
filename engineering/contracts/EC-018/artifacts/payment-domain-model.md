# Payment Domain Model

## Aggregate: Payment

`Payment` es el agregado raíz del Payment Context.

### Límites

- Propietario del ciclo de vida del cobro.
- Mantiene relación con `Order`, `Customer` y `Tenant`.
- Agrupa `PaymentAttempt` y `Refund`.
- Expone transiciones de estado y mantiene invariantes.

### Responsabilidades

- Iniciar el flujo de pago para una orden confirmada.
- Validar que el pago pertenece al mismo `tenantId` que la orden.
- Registrar `PaymentAttempt` y asociar resultados.
- Administrar el estado del pago: `CREATED`, `PENDING`, `AUTHORIZED`, `CAPTURED`, `SETTLED`, `FAILED`, `DECLINED`, `CANCELLED`, `REFUNDED`, `PARTIALLY_REFUNDED`.
- Emitir eventos de dominio en cada transición relevante.
- Gestionar idempotencia de operaciones.

### Invariantes

- Un `Payment` siempre pertenece a un `tenantId` válido.
- Un `Payment` siempre referencia una `orderId` confirmada.
- El total del pago no puede ser negativo.
- Una vez en estado terminal (`CANCELLED`, `DECLINED`, `FAILED`, `REFUNDED`) no se permite transición a estados de cobro.
- Cada `PaymentAttempt` debe tener un estado final claro y relacionarse con un proveedor.
- El `IdempotencyKey` solo puede ser reutilizado para la misma intención de pago; si la intención cambia, se crea un nuevo `Payment`.

## Entidades y Value Objects

- `Payment` (aggregate root)
- `PaymentAttempt` (entidad hija)
- `Refund` (entidad hija) 
- `PaymentMethod` (value object)
- `PaymentStatus` (value object)
- `PaymentReference` (value object)
- `IdempotencyKey` (value object)
- `Money` (value object)
- `OrderReference` (value object)
- `TenantId` (value object)

## Relaciones

- `Payment` 1:N `PaymentAttempt`
- `Payment` 1:N `Refund`
- `Payment` a `BillingRecord` / `Invoice` (desde Billing Context)
- `Payment` referencia `Order` y `Customer` de forma débil, sin poseer su modelo interno.

## Proceso conceptual

1. `Payment` se crea cuando una orden confirmada solicita pago.
2. Se genera un `PaymentAttempt` para un proveedor.
3. El intento puede terminar en `AUTHORIZED`, `DECLINED` o `FAILED`.
4. Si es autorizado, el pago puede capturarse.
5. Tras captura, el pago puede liquidarse y enviarse a Billing.
6. Reembolsos parciales o totales se registran como entidades vinculadas.

## Observaciones

- El agregado no guarda detalles sensibles de pago; solo referencias de proveedor y metadatos necesarios para reconciliación.
- Todos los datos de pago respetan el tenantId y los límites de consumo de la orden.
