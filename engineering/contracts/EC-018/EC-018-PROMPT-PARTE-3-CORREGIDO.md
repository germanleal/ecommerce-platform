# PROMPT DE IMPLEMENTACION

# ENGINEERING CONTRACT

# EC-018 - PAYMENTS & BILLING

## PARTE 3/4 - BILLING, REFUNDS & ORDER INTEGRATION

## 0. ALCANCE

Esta parte implementa Billing, Refunds y la integracion desacoplada entre Payment, Billing y Order Management.

EC-018 Parte 1 define el dominio y sus limites. EC-018 Parte 2 implementa Payment, PaymentAttempt, idempotencia y la abstraccion de Gateway. Esta parte extiende esos contratos sin alterar sus invariantes.

Esta parte no implementa proveedores reales de pagos ni facturacion electronica especifica de un pais.

## 1. Rol del agente

Actua como un equipo Enterprise compuesto por:

- Enterprise Architect
- Domain-Driven Design Architect
- Financial Systems Architect
- Billing Domain Expert
- Senior Backend Engineer
- Event-Driven Architecture Specialist
- Security Engineer
- QA Automation Engineer
- AI Engineering Specialist

## 2. Contexto y ownership

Los contratos aprobados son:

- EC-014 Marketplace Storefront;
- EC-016 Commerce Core;
- EC-017 Order Management;
- EC-018 Parte 1;
- EC-018 Parte 2.

Las responsabilidades son:

- Order Management es propietario de Order y de su ciclo de vida.
- Payment es propietario del ciclo de vida financiero del pago.
- Billing es propietario de la informacion de facturacion, Invoice y su ciclo de vida.
- Refund representa una operacion financiera relacionada con Payment y debe coordinarse con el Payment Gateway mediante el puerto aprobado en la Parte 2.

Ningun contexto puede modificar directamente el aggregate de otro contexto. Order no invoca directamente metodos internos de Payment o Billing. Billing tampoco actualiza directamente Order.

## 3. Objetivo

Implementar:

- Billing Context;
- Invoice Aggregate;
- Refund Aggregate y su flujo de procesamiento;
- integracion desacoplada entre Order, Payment y Billing;
- confirmacion financiera mediante eventos;
- notificacion a Order para que Order aplique sus propias transiciones;
- persistencia PostgreSQL, APIs REST, Kafka, seguridad y pruebas.

## 4. Restricciones

No implementar:

- SII Chile, SAT Mexico, AFIP Argentina, SUNAT Peru ni otra autoridad fiscal;
- proveedores de facturacion electronica;
- ERP, contabilidad o conciliacion bancaria;
- calculo de impuestos nacionales o reglas fiscales especificas;
- inventario, logistica, Marketplace o Commerce;
- llamadas sincrónicas para modificar el estado de Order;
- almacenamiento de PAN, CVV u otros datos sensibles de tarjetas.

Los montos y monedas deben reutilizar las reglas y tipos definidos por Payment. No duplicar logica financiera incompatible en Billing.

## 5. Billing Context

Implementar el Billing Context con estas responsabilidades:

- mantener informacion de facturacion;
- crear y consultar documentos comerciales no fiscales;
- mantener estados de facturacion;
- asociar Invoice con Payment, Order y Customer mediante referencias;
- publicar eventos de Billing.

Billing no autoriza, captura, cancela ni reembolsa pagos y no modifica Order.

## 6. Invoice Aggregate

Implementar `Invoice` como aggregate root con, como minimo:

- `id`;
- `tenantId`;
- `invoiceNumber`;
- `paymentId`;
- `orderId`;
- `customerId` como referencia externa;
- `currency`;
- `subtotal`;
- `total`;
- `status`;
- `issuedAt` opcional hasta la emision;
- `createdAt`;
- `updatedAt`.

Invariantes:

- `tenantId`, `paymentId`, `orderId`, moneda y montos son obligatorios;
- `subtotal` y `total` no son negativos;
- `total` debe ser consistente con el modelo comercial aprobado;
- `invoiceNumber` es unico dentro del tenant;
- una Invoice no puede emitirse antes de recibir confirmacion de Payment capturado o liquidado, segun la regla aprobada;
- una Invoice emitida no vuelve a `DRAFT`.

El modelo es agnostico de legislacion tributaria y no representa por si mismo un documento fiscal valido.

## 7. Billing Status y transiciones

Implementar:

`DRAFT`, `PENDING`, `ISSUED`, `CANCELLED`, `VOIDED`.

Transiciones minimas:

- `DRAFT` -> `PENDING`;
- `PENDING` -> `ISSUED` o `CANCELLED`;
- `ISSUED` -> `VOIDED`;
- `CANCELLED` y `VOIDED` son estados terminales.

No permitir emision duplicada para el mismo `tenantId` y `paymentId` o para el mismo `tenantId` y `orderId`, salvo una regla explicitamente aprobada mediante ADR.

## 8. Refund Aggregate

Implementar `Refund` como aggregate root con, como minimo:

- `id`;
- `tenantId`;
- `paymentId`;
- `orderId`;
- `refundReference`;
- `refundAmount`;
- `currency`;
- `reason`;
- `status`;
- `providerReference` opcional;
- `createdAt`;
- `updatedAt`;
- `processedAt` opcional.

`refundAmount` debe ser positivo y no puede superar el monto capturado o liquidado disponible, considerando refunds procesados y pendientes. Debe soportar refund parcial y refund total. La suma de refunds procesados y pendientes no puede exceder el monto reembolsable.

## 9. Refund Status y procesamiento

Implementar:

`REQUESTED`, `APPROVED`, `REJECTED`, `PROCESSED`, `FAILED`, `CANCELLED`.

Transiciones minimas:

- `REQUESTED` -> `APPROVED`, `REJECTED` o `CANCELLED`;
- `APPROVED` -> `PROCESSED`, `FAILED` o `CANCELLED`;
- `FAILED` -> `APPROVED` o `CANCELLED`;
- `REJECTED`, `PROCESSED` y `CANCELLED` son terminales.

El procesamiento debe invocar el puerto `PaymentGateway.refund` definido en la Parte 2 mediante un caso de uso o comando desacoplado. Billing no accede directamente a un SDK o proveedor concreto. La operacion debe ser idempotente y conservar el historial de intentos y referencias del proveedor cuando existan.

## 10. Integracion por eventos

Implementar la comunicacion entre contextos mediante eventos versionados, con envelope corporativo que incluya:

- `eventId`;
- `eventType` y `schemaVersion`;
- `tenantId`;
- `correlationId`;
- `causationId`;
- `aggregateId`;
- `occurredAt`;
- `payload`.

Flujo recomendado:

```text
OrderConfirmed
  -> PaymentRequested
  -> PaymentAuthorized
  -> PaymentCaptured
  -> PaymentCompleted
  -> InvoiceGenerated
  -> PaymentCompletedForOrder
  -> Order aplica su propia transicion
```

El flujo debe tolerar duplicados, eventos fuera de orden y reintentos mediante consumidores idempotentes, Inbox/Outbox o el mecanismo corporativo equivalente. No usar llamadas síncronas para modificar el estado financiero o el aggregate Order.

El evento dirigido a Order solo comunica un hecho de negocio. Order consume el evento y decide si la transicion de su propio aggregate es valida.

## 11. PaymentConfirmationService

Implementar `PaymentConfirmationService` como consumidor y coordinador de eventos. Sus responsabilidades son:

- recibir eventos de Payment;
- validar tenant, correlacion y estado;
- ignorar o tratar de forma idempotente duplicados;
- solicitar la creacion de Invoice cuando el pago cumpla la condicion aprobada;
- publicar eventos de Billing;
- publicar una notificacion para Order, sin modificar directamente el aggregate Order.

No debe actualizar una proyeccion de Order como si fuera el aggregate fuente. Si se necesita una vista local para consulta, debe ser una proyeccion read-only claramente separada y no una fuente de verdad.

## 12. Servicios y casos de uso

Implementar:

- `BillingService` para coordinacion de operaciones de Billing;
- `InvoiceService` para crear y consultar Invoice;
- `RefundService` para solicitar, aprobar, rechazar, cancelar y procesar Refund.

Casos de uso minimos:

- `GenerateInvoice`;
- `GetInvoice`;
- `RequestRefund`;
- `ApproveRefund`;
- `RejectRefund`;
- `ProcessRefund`;
- `GetRefund`;

Las operaciones mutantes deben aceptar y validar `Idempotency-Key`, reutilizando las reglas de idempotencia de la Parte 2 o una extension compatible y documentada.

## 13. Persistencia

Crear migraciones Flyway para:

- `invoices`;
- `refunds`;
- tablas auxiliares necesarias para Inbox/Outbox, auditoria o intentos, si forman parte de la arquitectura corporativa.

Incluir constraints, foreign keys, indices por tenant, unicidades y control de montos y monedas. Las referencias a Payment y Order no deben convertir a Billing en propietario de esos aggregates.

## 14. Repositorios

Implementar:

- `InvoiceRepository`;
- `RefundRepository`.

Todos los metodos de lectura y escritura deben recibir o derivar `tenantId`. Queda prohibido usar `findAll()` o consultas sin aislamiento por tenant.

## 15. Eventos y Kafka

Implementar como minimo:

- `InvoiceGeneratedEvent`;
- `InvoiceCancelledEvent`;
- `RefundRequestedEvent`;
- `RefundApprovedEvent`;
- `RefundRejectedEvent`;
- `RefundProcessedEvent`;
- `RefundFailedEvent`;
- `PaymentCompletedEvent`.

Crear o utilizar los topics:

- `billing.events`;
- `refund.events`.

Definir claves de particion, versionado, politica de reintentos, dead-letter topic y garantia de publicacion transaccional segun las convenciones existentes del proyecto. Los eventos no deben contener secretos ni datos de tarjeta.

## 16. API REST

Implementar con DTOs, validacion y OpenAPI:

- `POST /billing/invoices`;
- `GET /billing/invoices/{id}`;
- `GET /billing/invoices/order/{orderId}`;
- `POST /billing/refunds`;
- `GET /billing/refunds/{id}`;
- `POST /billing/refunds/{id}/approve`;
- `POST /billing/refunds/{id}/reject`;
- `POST /billing/refunds/{id}/process`.

No exponer entidades del dominio. Las respuestas deben ser consistentes, no revelar detalles internos y distinguir errores de validacion, autorizacion, conflicto de idempotencia y recurso inexistente.

## 17. Seguridad y multi-tenant

Integrar Keycloak, JWT y `TenantContext`. Permisos minimos:

- `BILLING_READ`;
- `INVOICE_CREATE`;
- `INVOICE_READ`;
- `REFUND_REQUEST`;
- `REFUND_APPROVE`;
- `REFUND_PROCESS`;
- `REFUND_REJECT`;
- `REFUND_CANCEL`.

Tenant A nunca puede consultar, modificar, aprobar, rechazar, cancelar ni procesar Invoices o Refunds de Tenant B. El tenant del token es la fuente de autoridad y no debe confiarse en un `tenantId` enviado por el cliente.

## 18. Observabilidad y auditoria

Registrar estructuradamente, sin informacion sensible:

- `invoiceId`;
- `refundId`;
- `paymentId`;
- `orderId`;
- `tenantId`;
- `correlationId`;
- `eventId`;
- resultado y duracion.

Crear metricas para invoices emitidas, refunds solicitados, aprobados, procesados y fallidos, errores de Billing y eventos rechazados por validacion.

Registrar auditoria de cambios de estado, actor, timestamp, correlationId y motivo, respetando las reglas de retencion existentes.

## 19. Testing

Implementar:

- unit tests de Invoice y Refund;
- application tests;
- repository tests;
- API tests;
- refund flow tests para parcial y total;
- billing flow tests;
- event integration tests;
- Inbox/Outbox e idempotency tests;
- cross-tenant tests;
- tests de eventos duplicados y fuera de orden;
- tests de limites de monto reembolsable;
- tests que demuestren que Order se actualiza mediante su propio consumidor y no por acceso directo.

Ejecutar la suite relevante y reportar resultados, cobertura y cualquier limitacion de infraestructura.

## 20. Documentacion

Crear o actualizar en `engineering/contracts/EC-018/artifacts/`:

- `billing-context.md`;
- `invoice-model.md`;
- `refund-model.md`;
- `billing-events.md`;
- `billing-api.md`;
- `billing-security.md`;
- `order-payment-integration.md`;
- `billing-database.md`;
- `billing-observability.md`.

Mantener consistencia con los artefactos de las Partes 1 y 2. Cualquier desviacion debe quedar registrada en un ADR.

## 21. Reglas para asistentes de IA

Nunca:

- implementar facturacion electronica especifica de un pais;
- calcular impuestos nacionales sin contrato aprobado;
- modificar directamente el aggregate Order;
- modificar Commerce o Marketplace;
- eliminar `tenantId`;
- acceder directamente a proveedores desde Billing;
- procesar un refund por encima del monto reembolsable;
- duplicar una Invoice o Refund por reentrega de eventos.

## 22. Validacion y Definition of Done

La Parte 3 esta completa cuando:

- Billing Context e Invoice Aggregate son funcionales;
- Refund Aggregate soporta refund parcial y total;
- las transiciones de Invoice y Refund se validan;
- Refund utiliza la abstraccion de Gateway de la Parte 2;
- la integracion entre Payment, Billing y Order es asincrona y basada en eventos;
- Order conserva el ownership de su ciclo de vida;
- existen consumidores idempotentes y control de duplicados;
- existen persistencia, Flyway, constraints, indices y aislamiento multi-tenant;
- existen APIs REST documentadas con OpenAPI;
- existen eventos Kafka versionados y observables;
- Keycloak, JWT y permisos estan integrados;
- las pruebas criticas fueron ejecutadas con resultados exitosos;
- la documentacion esta completa y alineada con las Partes 1 y 2.

## 23. Entregable para la Parte 4/4

La Parte 4 entregara:

- validacion end-to-end;
- revision de seguridad;
- auditoria financiera y de trazabilidad;
- observabilidad operativa;
- Architecture Governance Review;
- aprobacion formal del contrato EC-018.

## Estado esperado

`READY FOR PAYMENTS GOVERNANCE REVIEW`
