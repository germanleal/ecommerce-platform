# PROMPT DE IMPLEMENTACION

# ENGINEERING CONTRACT

# EC-018 - PAYMENTS & BILLING

## VERSION CONSOLIDADA: PARTES 1 Y 2 DE 4

Este documento corrige y consolida los requisitos de descubrimiento, modelado e implementacion inicial de Payments & Billing.

## 0. ALCANCE DE CADA PARTE

### Parte 1/4 - Descubrimiento y diseno del dominio

Esta parte produce decisiones de dominio y documentacion. No implementa codigo, APIs, persistencia ni integraciones con proveedores.

### Parte 2/4 - Payment Aggregate y abstraccion de Gateway

Esta parte implementa el dominio Payment y la infraestructura necesaria para operar con una abstraccion de proveedores. No implementa proveedores reales, Billing operativo, facturacion electronica ni Refunds como contexto implementado.

Las decisiones de la Parte 1 son la fuente de verdad para la Parte 2. Cualquier cambio de dominio requiere un ADR antes de modificar el contrato.

---

# PARTE 1/4 - PAYMENT DOMAIN DISCOVERY & BILLING CONTEXT

## 1. Rol del agente

Actua como un equipo Enterprise compuesto por:

- Enterprise Architect
- Domain-Driven Design Architect
- Solution Architect
- Payment Domain Expert
- Financial Systems Architect
- Event-Driven Architecture Specialist
- Security Architect
- Technical Writer
- QA Architect
- AI Engineering Specialist

La mision es analizar y disenar EC-018 - Payments & Billing.

## 2. Contexto y contratos aprobados

- EC-014 Marketplace Storefront es responsable de Stores, Catalog, Products y Categories.
- EC-016 Commerce Core es responsable de Sellable Product, Pricing, Shopping Cart y Commercial Rules.
- EC-017 Order Management es responsable de Order, Order Item, Checkout, Commercial Snapshot y Order Lifecycle.
- EC-018 Parte 1 define el modelo conceptual que implementa EC-018 Parte 2.

Order Management es el origen oficial de toda transaccion comercial. Payments consume una Order valida y su Commercial Snapshot; nunca modifica Order, Commerce, Marketplace, Shopping Cart ni Pricing.

El Customer puede ser referenciado como identidad externa mediante `customerId`, pero no se replica ni se convierte en una entidad propietaria de Payments salvo que otro contrato lo autorice expresamente.

## 3. Decisiones congeladas

- Domain-Driven Design.
- Arquitectura Hexagonal.
- Event-Driven Architecture.
- Spring Boot, PostgreSQL, Kafka, Flyway y Keycloak.
- `tenantId` obligatorio en toda entidad y evento de Payments.
- Payments consume Order; no modifica Order.
- Billing es un bounded context separado de Payment.
- No se almacenan PAN, CVV ni datos sensibles de tarjetas.

## 4. Payment Bounded Context

Definir responsabilidades, capacidades, invariantes, entradas, salidas y exclusiones del Payment Context.

Responsabilidades incluidas:

- representar un pago asociado a una Order;
- coordinar intentos de pago;
- aplicar idempotencia;
- abstraer la comunicacion con gateways;
- publicar eventos de Payment;
- mantener aislamiento por tenant.

Responsabilidades excluidas:

- modificar Order, Commerce o Marketplace;
- inventario, logistica o ERP;
- facturacion electronica e impuestos;
- integraciones concretas con proveedores en esta fase.

## 5. Billing Bounded Context

Definir `Billing Context` como contexto separado. Billing consume resultados de Payment mediante contratos o eventos y administra informacion financiera posterior al pago, invoices y estado de facturacion.

Billing no autoriza, captura, cancela ni reembolsa pagos. La implementacion de Billing queda fuera de la Parte 2 y se entrega en una parte posterior.

## 6. Lenguaje y modelo de dominio

Crear y documentar:

- Payment Domain Vision;
- Payment Boundaries;
- Payment Glossary;
- Payment Domain Model;
- Payment Lifecycle;
- Billing Model;
- Refund Model conceptual;
- Gateway Abstraction;
- Payment Events;
- Payment Security Model.

El glosario debe definir, como minimo, Payment, Payment Attempt, Payment Method, Gateway, Authorization, Capture, Settlement, Refund, Billing, Invoice, Payment Reference e Idempotency Key, incluyendo reglas y ejemplos.

## 7. Actores

Definir responsabilidades y permisos de Platform Administrator, Tenant Administrator, Customer, Payment Gateway, Customer Service y Finance Operator.

## 8. Payment Lifecycle conceptual

Documentar todas las transiciones validas, las condiciones de cada transicion y los estados terminales. Como minimo se consideran:

- `CREATED` -> `PENDING`;
- `PENDING` -> `AUTHORIZED`, `CAPTURED`, `FAILED` o `DECLINED`;
- `AUTHORIZED` -> `CAPTURED`, `CANCELLED` o `FAILED`;
- `CAPTURED` -> `SETTLED`, `REFUNDED` o `PARTIALLY_REFUNDED`;
- `SETTLED` -> `REFUNDED` o `PARTIALLY_REFUNDED`;
- `PARTIALLY_REFUNDED` -> `PARTIALLY_REFUNDED` o `REFUNDED`.

Los estados `FAILED`, `DECLINED`, `CANCELLED` y `REFUNDED` son terminales para la operacion en curso. Un reintento debe crear un nuevo PaymentAttempt y nunca reabrir silenciosamente un estado terminal.

## 9. Aggregate y conceptos relacionados

Disenar el aggregate root `Payment`, sus invariantes y limites. Disenar `PaymentAttempt` como entidad dependiente con relacion 1:N. Definir `PaymentMethod`, `Refund` conceptual e `IdempotencyKey`.

La idempotencia debe especificar alcance por tenant y operacion, hash de solicitud, expiracion, comportamiento ante reuso con payload distinto y respuesta repetida.

## 10. Eventos y seguridad conceptuales

Identificar eventos futuros, incluyendo PaymentCreated, PaymentAuthorized, PaymentCaptured, PaymentCancelled, PaymentFailed, PaymentRefunded e InvoiceGenerated, sin implementarlos en esta parte.

Definir permisos, TenantContext, aislamiento entre tenants y reglas para no exponer informacion sensible.

## 11. Artefactos de la Parte 1

Crear en `engineering/contracts/EC-018/artifacts/`:

- `payment-domain-vision.md`;
- `payment-boundaries.md`;
- `payment-lifecycle.md`;
- `payment-glossary.md`;
- `payment-domain-model.md`;
- `billing-model.md`;
- `refund-model.md`;
- `gateway-abstraction.md`;
- `payment-events.md`;
- `payment-security-model.md`.

## 12. Definition of Done de la Parte 1

La Parte 1 esta completa cuando existe una definicion formal de Payments, una separacion clara entre Payment y Billing, un lifecycle documentado, un modelo conceptual, un lenguaje ubicuo, una abstraccion de Gateway, un modelo de Refund conceptual, eventos identificados y reglas de seguridad y multi-tenant documentadas.

---

# PARTE 2/4 - PAYMENT AGGREGATE & GATEWAY ABSTRACTION

## 13. Rol y objetivo de implementacion

Actua como un equipo Enterprise compuesto por:

- Enterprise Software Architect
- Domain-Driven Design Architect
- Senior Backend Engineer
- Financial Systems Architect
- Payment Domain Expert
- PostgreSQL Architect
- Event-Driven Architecture Specialist
- Security Engineer
- QA Automation Engineer
- AI Engineering Specialist

Implementa completamente el Payment Context definido en la Parte 1, incluyendo Payment Aggregate, PaymentAttempt, PaymentMethod, idempotencia, persistencia, APIs, Kafka, seguridad y abstraccion de Gateway.

No implementes proveedores reales ni Billing operativo.

## 14. Restricciones de implementacion

No implementar:

- Stripe, Mercado Pago, PayPal, Adyen, Transbank ni otro proveedor real;
- facturacion electronica, impuestos, inventario, logistica o ERP;
- Refunds operativos en esta parte, salvo las reglas y extensiones estrictamente necesarias para preservar el lifecycle aprobado.

Crear adaptadores preparados, sin llamadas de red, credenciales, SDKs ni logica especifica de proveedor.

## 15. Estructura del servicio

Crear o extender:

```text
payment-service/
  src/main/java/com/company/platform/payment/
    domain/
    application/
    infrastructure/
    api/
  src/test/
```

Respetar los patrones y convenciones existentes del repositorio.

## 16. Payment Aggregate

Implementar `Payment` como aggregate root con, como minimo:

- `id`;
- `tenantId`;
- `orderId`;
- `customerId` opcional como referencia externa;
- `paymentReference` unica dentro del tenant;
- `status`;
- `currency`;
- `amount`;
- `paymentMethod`;
- `createdAt`;
- `updatedAt`.

El aggregate debe garantizar tenant obligatorio, order valida, monto positivo, moneda valida, referencia unica, estado consistente, no sobrepasar el monto original en operaciones futuras e idempotencia en los casos definidos por la aplicacion.

Payments debe consumir una Order valida y su snapshot comercial. No debe modificar la Order.

## 17. PaymentAttempt

Implementar `PaymentAttempt` como entidad dependiente de Payment con relacion 1:N y, como minimo:

- `id`;
- `paymentId`;
- `tenantId`;
- `provider`;
- `attemptNumber`;
- `status`;
- `providerReference` opcional;
- `requestedAt`;
- `completedAt` opcional;
- `failureReason` opcional.

`attemptNumber` debe ser monotono dentro de un Payment. Cada retry crea un nuevo intento y conserva el historial anterior.

## 18. PaymentMethod

Implementar un concepto extensible con valores iniciales:

- `CREDIT_CARD`;
- `DEBIT_CARD`;
- `BANK_TRANSFER`;
- `DIGITAL_WALLET`;
- `CASH_ON_DELIVERY`.

No almacenar PAN, CVV, track data ni secretos. Agregar metodos futuros no debe exigir cambios en las invariantes del aggregate.

## 19. Idempotencia

Implementar `IdempotencyKey` con:

- `id`;
- `tenantId`;
- `key`;
- `operation`;
- `requestHash`;
- `responseStatus`;
- `responseBody` o referencia segura al resultado;
- `createdAt`;
- `expiresAt`.

La unicidad minima es `(tenantId, operation, key)`. La misma clave con el mismo hash devuelve el mismo resultado. La misma clave con un hash distinto debe rechazarse. Las operaciones mutantes de Payment deben exigir idempotencia y registrar auditoria sin datos sensibles.

## 20. Gateway Abstraction

Definir el puerto `PaymentGateway` en el dominio o application core, sin dependencia de infraestructura. Debe soportar, como minimo:

- `authorize`;
- `capture`;
- `cancel`;
- `refund`;
- `getPaymentStatus`.

Las operaciones deben recibir un command o request del dominio y devolver un resultado normalizado, sin filtrar modelos de un proveedor concreto al dominio.

Crear adaptadores vacios preparados para implementacion futura:

- `StripeGatewayAdapter`;
- `MercadoPagoGatewayAdapter`;
- `PayPalGatewayAdapter`;
- `AdyenGatewayAdapter`;
- `TransbankGatewayAdapter`.

Cada adaptador implementa `PaymentGateway`, pero en esta fase debe fallar de forma controlada con una capacidad no configurada; no debe simular aprobaciones como si fueran pagos reales.

## 21. Aplicacion y casos de uso

Implementar los casos de uso:

- `CreatePayment`;
- `AuthorizePayment`;
- `CapturePayment`;
- `CancelPayment`;
- `GetPayment`;
- `ListPaymentsByOrder`;
- `RetryPayment`.

La coordinacion de persistencia, idempotencia, Gateway y eventos pertenece a la capa de aplicacion o a un servicio de dominio bien justificado. Nunca exponer entidades de dominio directamente por la API.

## 22. Persistencia y repositorios

Crear migraciones Flyway para:

- `payments`;
- `payment_attempts`;
- `idempotency_keys`.

Incluir constraints, foreign keys, indices por tenant y las unicidades definidas por el dominio. Todos los repositorios deben recibir y aplicar `tenantId`; queda prohibido usar `findAll()` o consultas sin aislamiento de tenant.

Implementar:

- `PaymentRepository`;
- `PaymentAttemptRepository`;
- `IdempotencyRepository`.

## 23. Estados y transiciones

Implementar los estados:

`CREATED`, `PENDING`, `AUTHORIZED`, `CAPTURED`, `SETTLED`, `FAILED`, `DECLINED`, `CANCELLED`, `REFUNDED`, `PARTIALLY_REFUNDED`.

Validar en codigo las transiciones aprobadas en la Parte 1. No permitir saltos arbitrarios, doble captura, cancelacion despues de un estado incompatible ni reembolso superior al monto capturado o liquidado.

## 24. Eventos y Kafka

Implementar, como minimo:

- `PaymentCreatedEvent`;
- `PaymentAuthorizedEvent`;
- `PaymentCapturedEvent`;
- `PaymentCancelledEvent`;
- `PaymentFailedEvent`;
- `PaymentRetryEvent`.

El evento de Refund se implementara cuando exista Refund operativo. Todos los eventos deben incluir `eventId`, `tenantId`, `correlationId`, `aggregateId`, `occurredAt` y `payload`, respetando el contrato corporativo. Publicar en `payment.events` con versionado y clave estable por aggregate.

## 25. API REST

Implementar con DTOs y OpenAPI:

- `POST /payments`;
- `GET /payments/{id}`;
- `GET /payments/order/{orderId}`;
- `POST /payments/{id}/authorize`;
- `POST /payments/{id}/capture`;
- `POST /payments/{id}/cancel`;
- `POST /payments/{id}/retry`.

Las operaciones mutantes deben aceptar y validar `Idempotency-Key`. Los errores deben mapearse a respuestas consistentes sin filtrar detalles internos ni datos sensibles.

## 26. Seguridad y multi-tenant

Integrar Keycloak, JWT y `TenantContext`. Validar autorizacion antes de cada operacion con estos permisos:

- `PAYMENT_CREATE`;
- `PAYMENT_READ`;
- `PAYMENT_AUTHORIZE`;
- `PAYMENT_CAPTURE`;
- `PAYMENT_CANCEL`;
- `PAYMENT_RETRY`.

Tenant A nunca puede consultar, autorizar, capturar, cancelar ni reintentar un Payment de Tenant B. El tenant derivado del token es la fuente de autoridad y no debe confiarse ciegamente en un tenant recibido por request.

## 27. Observabilidad

Registrar de forma estructurada `paymentId`, `orderId`, `tenantId`, `provider`, `correlationId`, `duration` y `status`, evitando secretos y datos de tarjeta.

Crear metricas para pagos creados, autorizaciones, capturas, cancelaciones, errores y reintentos.

## 28. Testing

Implementar:

- unit tests del dominio;
- application tests;
- repository tests;
- API tests;
- gateway mock tests;
- cross-tenant tests;
- idempotency tests;
- tests de transiciones validas e invalidas.

La cobertura minima del dominio es 80%. Ejecutar la suite relevante y reportar sus resultados.

## 29. Artefactos de la Parte 2

Crear o actualizar en `engineering/contracts/EC-018/artifacts/`:

- `payment-aggregate.md`;
- `payment-attempt.md`;
- `gateway-abstraction.md`;
- `idempotency-model.md`;
- `payment-api.md`;
- `payment-events.md`;
- `payment-database.md`;
- `payment-security.md`.

Los artefactos de la Parte 1 deben permanecer consistentes con la implementacion. Si una decision cambia, crear un ADR y actualizar ambos conjuntos de documentos.

## 30. Reglas para asistentes de IA

Nunca:

- implementar proveedores reales;
- almacenar PAN, CVV o informacion sensible de tarjetas;
- modificar Order, Commerce o Marketplace;
- eliminar `tenantId`;
- introducir consultas sin aislamiento de tenant;
- exponer entidades del dominio en la API;
- ocultar fallos de un Gateway no configurado simulando pagos exitosos.

## 31. Validacion y Definition of Done de la Parte 2

La Parte 2 esta completa cuando:

- Payment Aggregate y PaymentAttempt son funcionales;
- PaymentMethod es extensible y seguro;
- la abstraccion de Gateway esta desacoplada;
- no existen integraciones reales con proveedores;
- la idempotencia funciona para exito, repeticion y conflicto de hash;
- existe persistencia PostgreSQL con Flyway, constraints, indices y aislamiento por tenant;
- existen APIs REST documentadas con OpenAPI;
- los eventos se publican con el contrato corporativo;
- la seguridad Keycloak/JWT y los permisos estan integrados;
- las transiciones de estado se validan;
- los tests relevantes fueron ejecutados y cumplen la cobertura minima;
- la documentacion tecnica esta completa y consistente.

## 32. Entregable para la Parte 3/4

La siguiente parte implementara, como minimo:

- Billing Context;
- Refunds operativos;
- Invoice Model;
- integracion Order -> Payment;
- confirmacion de pago mediante eventos;
- actualizacion del ciclo de vida de Order mediante el contrato aprobado.

## Estado esperado

`READY FOR BILLING & ORDER INTEGRATION`

