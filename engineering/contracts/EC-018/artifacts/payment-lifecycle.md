# Payment Lifecycle

## Estados principales

- CREATED
- PENDING
- AUTHORIZED
- CAPTURED
- SETTLED

## Estados alternativos

- FAILED
- DECLINED
- CANCELLED
- REFUNDED
- PARTIALLY_REFUNDED

## Definición de estados

- CREATED: Pago inicial registrado, esperando intento contra proveedor.
- PENDING: Solicitud enviada al proveedor y respuesta en curso.
- AUTHORIZED: Pago autorizado por el proveedor, monto retenido pero no cobrado.
- CAPTURED: Monto cobrado y confirmado por el proveedor.
- SETTLED: Cobro liquidado y listo para facturación financiera en Billing.
- FAILED: Error técnico o de comunicación en el intento de pago.
- DECLINED: Proveedor rechazó la autorización.
- CANCELLED: Pago cancelado antes de captura.
- REFUNDED: Total del pago reintegrado.
- PARTIALLY_REFUNDED: Parte del pago reintegrada.

## Transiciones válidas

| Desde | Evento / Acción | Hacia | Regla | Nota |
|---|---|---|---|---|
| CREATED | iniciar intento | PENDING | Se crea un `PaymentAttempt` | |
| PENDING | autorizado | AUTHORIZED | Proveedor aprueba la retención | |
| PENDING | declinado | DECLINED | Proveedor rechaza la autorización | |
| PENDING | error | FAILED | Fallo técnico o timeout | |
| AUTHORIZED | capturar | CAPTURED | Confirmación de cobro exitosa | |
| AUTHORIZED | cancelar | CANCELLED | Si el proveedor permite reversión antes de captura | |
| CAPTURED | liquidar | SETTLED | Confirmación de settlement o reconciliación | |
| CAPTURED | solicitar reembolso | PARTIALLY_REFUNDED | Se inicia refund parcial | |
| CAPTURED | solicitar reembolso | REFUNDED | Se inicia refund total | |
| SETTLED | solicitar reembolso | PARTIALLY_REFUNDED | Ajuste financiero tras settlement | |
| SETTLED | solicitar reembolso | REFUNDED | Ajuste financiero total tras settlement | |
| CREATED/PENDING | cancelar | CANCELLED | Pago cancelado antes de autorización/captura | |

## Reglas de transición

- `CREATED` es el estado inicial del agregado Payment.
- Solo `PENDING` puede evolucionar a `AUTHORIZED`, `DECLINED` o `FAILED`.
- Solo `AUTHORIZED` puede avanzar a `CAPTURED` o `CANCELLED`.
- Solo `CAPTURED` o `SETTLED` pueden originar reembolsos.
- `REFUNDED` y `PARTIALLY_REFUNDED` son estados terminales para el pago o para el ajuste del invoice.
- `CANCELLED`, `DECLINED`, `FAILED` son estados terminales sin captura.

## Vistas de estado

- `PENDING` significa que la interacción con el proveedor no ha concluido.
- `AUTHORIZED` indica que el monto está reservado, pero aún no se ha cobrado.
- `CAPTURED` indica que el cobro fue exitoso.
- `SETTLED` indica que la operación fue liquidada y puede ser registrada en Billing.
- `REFUNDED` y `PARTIALLY_REFUNDED` indican que el dinero fue devuelto en su totalidad o en parte.

## Validaciones de estado

- No se permite captura directa sin pasar por autorización si el flujo de provider lo requiere.
- No se permite reembolso antes de `CAPTURED` o `SETTLED`.
- Un pago `CANCELLED` no puede retornar a `PENDING`.
- Un pago `DECLINED` no puede ser reintentado como el mismo `Payment`; se debe crear un nuevo `Payment` con idempotencia separada.
