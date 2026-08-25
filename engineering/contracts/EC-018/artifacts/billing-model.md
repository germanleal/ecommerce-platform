# Billing Model

## Propósito

El Billing Context administra la información financiera posterior al pago, manteniendo el historial de liquidación y el estado de invoices sin procesar pagos.

## Entidades principales

- `BillingRecord`: registro de la reconciliación financiera de un pago.
- `Invoice`: representación contable de una transacción asociada a una `Order` y a un `Payment`.
- `BillingStatus`: estado del registro de facturación.
- `SettlementReference`: referencia a la liquidación del proveedor.

## Responsabilidades

- Crear y actualizar `BillingRecord` e `Invoice` a partir de pagos capturados o liquidados.
- Registrar el estado financiero de la transacción.
- Enlazar `Invoice` con `Order`, `Payment` y `tenantId`.
- Marcar ajustes de reembolso y estados de invoice.
- Exponer datos de facturación a finanzas y contabilidad.

## Estados de billing

- DRAFT: registro financiero preliminar.
- OPEN: invoice abierto y pendiente de liquidación o revisión.
- PAID: se reconoce el pago financiero.
- ADJUSTED: invoice ajustado por reembolso parcial.
- CREDITED: invoice con nota de crédito por reembolso total.
- VOIDED: invoice cancelado sin efecto financiero.

## Reglas

- Billing nunca procesa autorizaciones ni capturas.
- Billing no modifica el estado de `Payment`.
- Billing consume eventos de `PaymentCapturedEvent`, `PaymentSettledEvent`, `PaymentRefundedEvent`.
- Un `BillingRecord` y su `Invoice` deben tener el mismo `tenantId` que el `Payment`.
- El importe del invoice se deriva de `Payment` y del snapshot de orden; no recalcula precio.

## Relaciones con otros dominios

- `Billing` consume eventos de `Payment`.
- `Billing` lee hechos de `Order` para vincular la transacción con la orden confirmada y su cliente.
- `Billing` provee datos de estado financiero a futuros ERP y contabilidad.

## Observaciones

- En esta fase, `Invoice` es un modelo de registro financiero y no un documento fiscal.
- El billing model puede evolucionar para integrar estados de liquidación con sistemas contables externos sin cambiar el procesamiento de pagos.
