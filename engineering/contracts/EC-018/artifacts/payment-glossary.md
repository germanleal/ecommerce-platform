# Payment Glossary

| Término | Definición | Regla | Ejemplo |
|---|---|---|---|
| Payment | Agregado que representa el intento de cobrar una orden confirmada | Un `Payment` pertenece a un `tenantId` y a una `orderId` | `PAY-2026-0001` |
| Payment Attempt | Registro de cada interacción con un proveedor de pago | Existe una relación 1:N con `Payment` | Intento de autorización con Stripe | 
| Payment Method | Medio usado para realizar el pago | No contiene detalles sensibles de tarjeta en el dominio | `Credit Card`, `Digital Wallet`, `Bank Transfer` |
| Gateway | Abstracción que traduce operaciones de pago a un proveedor específico | El dominio no depende de un proveedor concreto | `StripeAdapter` |
| Authorization | Aprobación de un monto por parte del proveedor | Se obtiene antes de la captura cuando el proveedor lo exige | `AUTH` en tarjeta de crédito |
| Capture | Acción de convertir una autorización en cobro efectivo | Solo ocurre después de una autorización válida | Capturar USD 100 autorizados |
| Settlement | Confirmación de que el cobro ha sido liquidado en el circuito financiero | `SETTLED` habilita registro en Billing | `Settle` de un pago capturado |
| Refund | Reintegro total o parcial de un pago ya cobrado | Debe referenciar un `Payment` y un `PaymentAttempt` original | Reembolso parcial de USD 20 |
| Billing | Contexto de registro financiero posterior al pago | No procesa pagos ni autorizaciones | `Invoice` y estados de liquidación |
| Invoice | Registro financiero que vincula orden, pago y estado de facturación | No es un documento fiscal obligatorio en esta fase | `INV-2026-0001` |
| Payment Reference | Identificador externo usado por el proveedor | Se genera al interactuar con el gateway | Token de autorización de PayPal |
| Idempotency Key | Clave de operación para evitar duplicados | Reutilizable solo para la misma intención de pago | Mismo carrito y orden en un reintento |
