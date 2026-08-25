# Payment Security Model

## Permisos

- `PAYMENT_CREATE`: crear un pago para una orden confirmada.
- `PAYMENT_READ`: consultar pagos y su estado.
- `PAYMENT_CANCEL`: cancelar un pago antes de captura.
- `PAYMENT_REFUND`: iniciar reembolsos parciales o totales.
- `PAYMENT_CAPTURE`: capturar un pago autorizado.
- `BILLING_READ`: consultar invoices y registros de facturación.
- `BILLING_ADJUST`: ajustar invoices por reembolsos.

## Alcance de cada permiso

- `PAYMENT_CREATE`: permite al actor iniciar un flujo de pago solo dentro de su `tenantId`.
- `PAYMENT_READ`: permite ver pagos, intentos y estados.
- `PAYMENT_CANCEL`: permite cancelar pagos `CREATED` o `PENDING` si la política de proveedor lo permite.
- `PAYMENT_REFUND`: permite iniciar reembolsos sobre pagos capturados o liquidados.
- `PAYMENT_CAPTURE`: permite confirmar cobros autorizados.
- `BILLING_READ`: acceso a información de billing e invoices.
- `BILLING_ADJUST`: modificar el estado financiero de un invoice cuando se produce un reembolso.

## Actores y permisos

- Platform Administrator: `PAYMENT_READ`, `BILLING_READ`, `BILLING_ADJUST` (supervisión global, sujeto a tenantId según políticas corporativas).
- Tenant Administrator: `PAYMENT_READ`, `BILLING_READ`, `BILLING_ADJUST` dentro de su tenant.
- Customer: `PAYMENT_CREATE`, `PAYMENT_READ`, `PAYMENT_REFUND` para sus propias órdenes.
- Customer Service: `PAYMENT_READ`, `PAYMENT_CANCEL`, `PAYMENT_REFUND`, `BILLING_READ` dentro de su tenant.
- Finance Operator: `BILLING_READ`, `BILLING_ADJUST` dentro de su tenant.
- Payment Gateway: acceso a la interfaz de integración, pero no a permisos de negocio internos; se considera un actor externo de infraestructura.

## Reglas de seguridad

- Todas las entidades de Payments y Billing contienen `tenantId` y deben validar aislamiento completo.
- Ninguna operación puede ejecutarse fuera del tenant del actor salvo que el rol sea explícitamente cross-tenant y autorizado.
- El dominio no almacena datos sensibles de tarjeta. Los detalles sensibles se delegan a los proveedores o a la capa de infraestructura segura.
- Los permisos se aplican a operaciones de dominio, no a detalles de implementación de gateway.
