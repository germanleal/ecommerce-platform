# Payment Events

## Principio

Los eventos de dominio representan hechos ocurridos en el ciclo de pago. Son contratos de integración con otros contextos.

## Eventos identificados

- `PaymentCreatedEvent`: se emite cuando un pago se registra para una orden confirmada.
- `PaymentAttemptCreatedEvent`: se emite al iniciar un intento de pago.
- `PaymentAuthorizedEvent`: se emite cuando un proveedor autoriza un pago.
- `PaymentCapturedEvent`: se emite cuando el cobro es capturado.
- `PaymentFailedEvent`: se emite cuando un intento de pago falla por error técnico.
- `PaymentDeclinedEvent`: se emite cuando un proveedor rechaza la autorización.
- `PaymentCancelledEvent`: se emite cuando un pago se cancela antes de captura.
- `PaymentSettledEvent`: se emite cuando el pago se liquida y está listo para Billing.
- `PaymentRefundedEvent`: se emite cuando un reembolso total se completa.
- `PaymentPartiallyRefundedEvent`: se emite cuando un reembolso parcial se completa.
- `InvoiceCreatedEvent`: se emite cuando Billing crea un invoice basado en un pago.
- `InvoiceAdjustedEvent`: se emite cuando Billing ajusta un invoice por reembolso.

## Notas

- `PaymentEvents` contienen `eventId`, `eventType`, `aggregateId`, `tenantId`, `occurredAt` y metadatos de correlación.
- Los eventos no exponen datos internos de proveedores.
- Billing puede suscribirse a `PaymentCapturedEvent`, `PaymentSettledEvent`, `PaymentRefundedEvent` y `PaymentPartiallyRefundedEvent`.
- Otros consumidores potenciales: Analytics, Fraud Detection, Customer Service.
