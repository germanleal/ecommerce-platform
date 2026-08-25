# Domain Policy: AutoCancelOrderOnPaymentFailure

## Objetivo
Garantizar que el inventario reservado sea liberado si el pago del pedido no se concreta en el tiempo establecido.

## Contexto
Relación entre **Payments & Billing** y **Order Fulfillment**.

## Actor
Sistema (Automático).

## Evento Disparador
`PaymentFailed` o `PaymentExpired`.

## Resultado Esperado
1. El estado del Agregado `Order` transiciona a `CANCELLED`.
2. Se emite un evento `OrderCancelled` que el contexto de Inventario escuchará para liberar el stock.

## Restricciones
- Solo aplica a pedidos en estado `AWAITING_PAYMENT`.
