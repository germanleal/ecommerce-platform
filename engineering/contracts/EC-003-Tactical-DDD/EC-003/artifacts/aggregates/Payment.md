# Aggregate Root: Payment

## 1. Identificación
- **Nombre**: Payment
- **Contexto**: Payments & Billing
- **Objetivo**: Gestionar la transacción financiera asociada a un pedido o suscripción.
- **Responsabilidad principal**: Registrar la intención de pago, interactuar con pasarelas externas y confirmar el resultado financiero.

## 2. Límites
- **Qué pertenece**: Monto a cobrar, moneda, método de pago, referencia externa de transacción.
- **Qué NO pertenece**: Detalle de productos comprados, dirección de envío.
- **Qué reglas protege**: Correspondencia de montos, unicidad de transacción por pedido.
- **Qué datos controla**: `payment_id`, `order_id`, `amount`, `currency`, `gateway_reference`, `status`.
- **Qué comportamiento encapsula**: Autorización, captura, reembolso.

## 3. Invariantes
- **BR-PAY-01**: Un pago no puede ser marcado como `COMPLETED` sin una referencia válida de la pasarela externa.
- **BR-PAY-02**: El monto del pago debe coincidir exactamente con el monto solicitado por el BC de Origen.

## 4. Ciclo de Vida
### Estados
- **PENDING**: Intención de pago registrada.
- **AUTHORIZED**: Fondos retenidos pero no capturados.
- **COMPLETED**: Fondos transferidos exitosamente.
- **FAILED**: Error en la transacción.
- **REFUNDED**: Fondos devueltos al cliente.

## 5. Responsabilidades
- **Qué hace**: Garantiza que el dinero sea procesado correctamente.
- **Qué NO hace**: No decide si una orden es válida o no.

## 6. Relaciones
- Se vincula mediante `order_id` con el BC de `Order Fulfillment`.
