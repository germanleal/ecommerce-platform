# EC-004 Parte 3 — Saga de pedido

El proceso objetivo es:

```text
OrderRequested → StockReservationRequested → StockReserved
              → PaymentRequested → PaymentCompleted → OrderConfirmed
```

Compensaciones:

- `StockReservationFailed` o `InventoryRejected`: rechazar/cancelar pedido.
- `PaymentFailed`: liberar reserva y rechazar/cancelar pedido.
- Timeout: reintentar con backoff y enviar a DLQ para recuperación manual.

## Estado actual

El repositorio contiene `CheckoutOrchestrator` y publishers Kafka, pero no contiene todavía un Saga Orchestrator completo que correlacione reserva, pago, compensación y transición del pedido. Estado: **pendiente de implementación**.
