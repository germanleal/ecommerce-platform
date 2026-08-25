# Payment Domain Vision

## Propósito

El dominio Payments garantiza que un pedido pueda ser pagado de forma segura, consistente y auditable, usando una abstracción de proveedores de pago, con idempotencia y aislamiento multi-tenant.

## Visión

Crear un servicio de pagos que consuma órdenes confirmadas, administre el ciclo de vida de los cobros y permita posteriores conciliaciones financieras sin acoplarse a un proveedor específico.

## Objetivos

- Registrar y orquestar el procesamiento de una transacción de pago.
- Validar y mantener la trazabilidad de cada intento de pago.
- Soportar múltiples proveedores mediante una abstracción de gateway.
- Proteger contra ejecuciones duplicadas con idempotencia.
- Manejar reembolsos parciales y totales.
- Proveer eventos de dominio para downstream y Billing.

## Capacidades

- Crear `Payment` a partir de una orden confirmada.
- Registrar `PaymentAttempt` por cada interacción con un proveedor.
- Gestionar autorizaciones, capturas y cancelaciones.
- Iniciar y registrar reembolsos.
- Emitir eventos de dominio de pago.
- Validar tenantId en todas las operaciones.

## Principios

- Separación clara entre pago y facturación.
- Pago como dominio de dinero en movimiento, no de facturación.
- Contractor Payment consume órdenes, nunca las modifica.
- Independencia de proveedor: no hay dependencia directa a Stripe, PayPal, Transbank o similar.
- Aislamiento de tenant: cada entidad y evento contiene `tenantId`.
- Idempotencia como principio de seguridad y confiabilidad.

## Actores

- Platform Administrator: supervisa la plataforma completa sin evadir aislamiento tenant.
- Tenant Administrator: configura políticas de pago y visualiza pagos/facturación de su tenant.
- Customer: inicia pagos y solicita reembolsos para sus órdenes.
- Payment Gateway: proveedor externo abstracto que procesa autorizaciones, capturas y reembolsos.
- Customer Service: apoya a clientes con disputas y verificaciones de pago.
- Finance Operator: revisa registros financieros, facturas y conciliaciones.
