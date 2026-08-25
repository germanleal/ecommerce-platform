# Refund Model

## Propósito

Definir cómo se representan y gestionan los reembolsos en relación con un pago exitoso.

## Conceptos

- `Refund`: entidad que representa la devolución de dinero a un cliente.
- `RefundType`: parcial o total.
- `RefundStatus`: PENDING, COMPLETED, FAILED.
- `RefundReference`: identificador del reembolso en el proveedor.

## Reglas

- Un reembolso siempre referencia un `Payment` existente.
- Solo pagos en estado `CAPTURED` o `SETTLED` pueden recibir reembolsos.
- Un pago puede tener múltiples reembolsos si no exceden el monto cobrado.
- La suma de los reembolsos no puede superar el monto capturado.
- Un reembolso total cambia el estado del pago a `REFUNDED`.
- Un reembolso parcial cambia el estado del pago a `PARTIALLY_REFUNDED`.
- Reembolsos fallidos deben poder volver a intentarse con nueva `RefundReference`.

## Flujo

1. Se solicita un `Refund` para un `Payment` capturado o liquidado.
2. Se crea la entidad de reembolso con estado `PENDING`.
3. El gateway procesa la devolución y devuelve un `RefundReference`.
4. El reembolso se marca como `COMPLETED` o `FAILED`.
5. El estado del `Payment` se actualiza a `REFUNDED` o `PARTIALLY_REFUNDED`.
6. Billing actualiza el `Invoice` o crea una nota de crédito si aplica.

## Relación con Payment

- `Payment` 1:N `Refund`
- Cada `Refund` registra monto, moneda, motivo, estado y referencia a `PaymentAttempt` si aplica.
- Un `Refund` parcial tiene monto menor al cobrado; un `Refund` total iguala el monto total del pago.

## Observaciones

- El modelo de reembolso es independiente de la lógica del proveedor.
- El dominio mantiene solo la referencia necesaria al proveedor para reconciliación.
- Billing trata los reembolsos como ajustes financieros posteriores al cobro.
