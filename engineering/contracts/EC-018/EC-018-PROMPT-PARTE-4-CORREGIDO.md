# PROMPT DE IMPLEMENTACION

# ENGINEERING CONTRACT

# EC-018 - PAYMENTS & BILLING

## PARTE 4/4 - PAYMENTS GOVERNANCE, COMPLIANCE & PRODUCTION READINESS

## 0. OBJETIVO Y REGLA DE APROBACION

Esta fase no agrega funcionalidades de negocio. Valida, estabiliza y aprueba formalmente los desarrollos implementados en EC-018 Partes 1, 2 y 3.

El estado `APPROVED` solo puede asignarse cuando existe evidencia verificable de cada criterio de este documento. Si un criterio falla o carece de evidencia, el resultado debe ser `BLOCKED` o `CONDITIONAL`, indicando responsable, impacto, prioridad y plan de cierre.

No se debe declarar aprobacion por la sola existencia de codigo, documentacion o tests nominales.

## 1. Rol del agente

Actua como un Architecture Governance Board Enterprise compuesto por:

- Enterprise Architect;
- Chief Software Architect;
- Financial Systems Architect;
- Security Architect;
- DevSecOps Architect;
- Platform Architect;
- Domain-Driven Design Architect;
- QA Lead;
- Compliance Officer;
- Technical Writer;
- AI Engineering Specialist.

## 2. Contexto y alcance

Contratos relacionados:

- EC-014 Marketplace Storefront;
- EC-014.5 Platform Validation & MVP Hardening;
- EC-016 Commerce Core;
- EC-017 Order Management;
- EC-018 Parte 1;
- EC-018 Parte 2;
- EC-018 Parte 3.

EC-018 debe cubrir, como mínimo:

- Payment Aggregate;
- PaymentAttempt;
- PaymentMethod;
- Idempotency;
- Gateway Abstraction;
- Billing Context;
- Invoice Aggregate;
- Refund Aggregate;
- integracion asincrona con Order;
- APIs REST;
- eventos Kafka;
- seguridad y aislamiento multi-tenant;
- persistencia, observabilidad y pruebas.

La revision no implementa proveedores reales de pago, facturacion electronica, ERP, contabilidad, inventario ni logistica.

## 3. Invariantes de ownership

Validar y documentar que:

- Order Management es propietario de Order y de su ciclo de vida.
- Payment es propietario del estado financiero del pago.
- Billing es propietario de Invoice y de la informacion de facturacion.
- Refund es propietario de la solicitud y procesamiento del reembolso dentro del contrato aprobado.
- Ningun contexto modifica directamente el aggregate de otro contexto.
- Order consume hechos publicos y aplica sus propias transiciones; Payments y Billing no escriben el aggregate Order.
- Commerce, Marketplace, Shopping Cart, Pricing, Inventory y Logistics quedan fuera de Payments & Billing.

## 4. Matriz de trazabilidad obligatoria

Crear una matriz que relacione cada requisito de las Partes 1, 2 y 3 con:

- implementacion o artefacto;
- prueba que lo valida;
- evidencia de ejecucion;
- estado `PASS`, `FAIL`, `BLOCKED` o `WAIVED`;
- riesgo y responsable si no esta completo.

La matriz debe cubrir expresamente:

| Area | Evidencia minima |
| --- | --- |
| Domain boundaries | documentos de limites y revision arquitectonica |
| Payment | aggregate, invariantes, estados y pruebas de transicion |
| PaymentAttempt | persistencia, numeracion monotona e historial de retry |
| Idempotency | repeticion exitosa, hash conflictivo, expiracion y concurrencia |
| Gateway | puerto, adaptadores vacios y ausencia de SDKs reales |
| Billing | Invoice, estados, unicidad y pruebas |
| Refund | parcial, total, limites, estados y Gateway refund |
| Order integration | eventos, consumidores idempotentes y ownership de Order |
| Kafka | envelope, versionado, reintentos y dead letter |
| API | OpenAPI, DTOs, validaciones y codigos HTTP |
| Security | JWT, Keycloak, permisos y TenantContext |
| Database | Flyway, constraints, indices y foreign keys |
| Observability | logs, traces, metricas, health checks y alertas |
| Documentation | artefactos de las Partes 1, 2, 3 y cierre |

## 5. Domain Architecture Review

Comprobar que Payments & Billing administra exclusivamente Payment, PaymentAttempt, Billing, Invoice y Refund, junto con sus eventos y procesos asociados.

Comprobar la ausencia de dependencias circulares y de dependencias de infraestructura desde el dominio. La direccion esperada es:

```text
Marketplace -> Commerce -> Order -> Payment -> Billing
                                      |
                                      +-> eventos publicos para Order y Billing
Billing -> ERP/Accounting/Tax providers (futuro)
```

Las flechas representan contratos de integracion; no autorizan llamadas sincrónicas para modificar aggregates remotos.

## 6. End-to-end Validation

Ejecutar y conservar evidencia de estos escenarios.

### Escenario 1: pago confirmado

```text
Cliente autenticado
  -> Cart valido
  -> Checkout
  -> Order creada
  -> PaymentRequested
  -> Payment creado
  -> Payment autorizado
  -> Payment capturado
  -> PaymentCompleted
  -> Invoice generada
  -> evento publicado para Order
  -> Order aplica su propia transicion
```

Verificar correlacion, tenant, idempotencia, orden logico, persistencia y estados en cada paso.

### Escenario 2: rechazos y conflictos

Validar pago duplicado, Idempotency-Key repetida con el mismo payload, Idempotency-Key con hash distinto, tenant incorrecto, monto invalido, moneda invalida, captura duplicada y transicion de estado invalida. Cada caso debe producir el error contractual esperado sin efectos parciales.

### Escenario 3: refunds

Ejecutar refund parcial y refund total. Verificar limite reembolsable, estados, invocacion idempotente de Gateway, eventos, auditoria y consistencia entre Payment, Refund e Invoice.

### Escenario 4: fallos y recuperacion

Simular timeout, evento duplicado, evento fuera de orden, consumer caido, reintento agotado y mensaje enviado a dead-letter. Verificar que no se duplique Invoice o Refund y que el sistema sea recuperable.

## 7. Payment Lifecycle Review

Validar en codigo y mediante pruebas todas las transiciones aprobadas:

- `CREATED` -> `PENDING`;
- `PENDING` -> `AUTHORIZED`, `CAPTURED`, `FAILED` o `DECLINED`;
- `AUTHORIZED` -> `CAPTURED`, `CANCELLED` o `FAILED`;
- `CAPTURED` -> `SETTLED`, `REFUNDED` o `PARTIALLY_REFUNDED`;
- `SETTLED` -> `REFUNDED` o `PARTIALLY_REFUNDED`;
- `PARTIALLY_REFUNDED` -> `PARTIALLY_REFUNDED` o `REFUNDED`.

Intentar transiciones invalidas, doble captura, cancelacion incompatible y refunds superiores al monto disponible. Todas deben rechazarse de forma determinista.

## 8. Billing y consistencia financiera

Validar los lifecycles de Invoice y Refund, sus invariantes y su relacion con Payment y Order.

Comprobar que:

- no se emite Invoice antes de la confirmacion financiera requerida;
- no existe mas de una Invoice para la misma clave de negocio aprobada;
- la suma de refunds procesados y pendientes no supera el monto reembolsable;
- currency y montos son consistentes entre los contextos;
- los eventos duplicados no crean efectos duplicados;
- una inconsistencia queda visible, auditable y recuperable.

## 9. Multi-tenant Validation

Crear al menos dos tenants aislados con Payment, Invoice y Refund propios. Validar que Tenant A no pueda consultar, modificar, aprobar, cancelar ni procesar recursos de Tenant B.

Revisar codigo, repositorios, indices, filtros, consumidores Kafka, logs, metricas, caches y endpoints. El tenant derivado de JWT/TenantContext es la fuente de autoridad; nunca confiar solo en un `tenantId` del request.

## 10. Security Review

Validar Keycloak, JWT, TenantContext, autenticacion y autorizacion para:

- `PAYMENT_CREATE`;
- `PAYMENT_READ`;
- `PAYMENT_AUTHORIZE`;
- `PAYMENT_CAPTURE`;
- `PAYMENT_CANCEL`;
- `PAYMENT_RETRY`;
- `BILLING_READ`;
- `INVOICE_CREATE`;
- `INVOICE_READ`;
- `REFUND_REQUEST`;
- `REFUND_APPROVE`;
- `REFUND_REJECT`;
- `REFUND_PROCESS`;
- `REFUND_CANCEL`.

Probar acceso permitido, acceso denegado, token expirado, tenant manipulado, rol insuficiente y replay de una operacion mutante.

## 11. Event y Kafka Review

Validar eventos de Payment, Billing, Refund y Order integration. Cada evento debe incluir como minimo:

- `eventId`;
- `eventType`;
- `schemaVersion`;
- `aggregateId`;
- `tenantId`;
- `correlationId`;
- `causationId`;
- `occurredAt`;
- `payload`.

Validar topics `payment.events`, `billing.events` y `refund.events`, claves de particion, orden logico por aggregate, consumidores idempotentes, reintentos, backoff, dead-letter strategy, observabilidad y compatibilidad de esquema.

No aprobar si los eventos se publican antes de confirmar la transaccion local cuando el contrato exige Outbox o publicacion transaccional.

## 12. API Review

Validar todos los endpoints de Payment, Billing e integracion de Refund definidos en las Partes 2 y 3, incluyendo:

- OpenAPI actualizado;
- DTOs sin entidades expuestas;
- validaciones de entrada;
- `Idempotency-Key` en operaciones mutantes;
- codigos HTTP consistentes;
- errores de dominio y conflicto de idempotencia;
- paginacion y filtros tenant-aware cuando corresponda;
- versionado y compatibilidad hacia atras.

## 13. Database Review

Validar tablas `payments`, `payment_attempts`, `idempotency_keys`, `invoices` y `refunds`.

Revisar Flyway, constraints, foreign keys, indices por tenant, unicidades, precision monetaria, currency, timestamps, auditoria, transacciones, concurrencia y rollback seguro de migraciones. Comprobar que no existan PAN, CVV ni secretos persistidos.

## 14. Observability Review

Validar logs estructurados, tracing distribuido, health checks, metricas, alertas y dashboard readiness. La trazabilidad debe conservar `correlationId`, `causationId`, `tenantId`, `orderId`, `paymentId`, `invoiceId` y `refundId`, sin filtrar informacion sensible.

Como minimo, medir creacion, autorizacion, captura, fallos, retries, invoices, refunds, latencia, consumer lag, dead-letter messages y errores por tenant o provider abstraction.

## 15. Compliance Readiness Review

No certificar cumplimiento. Preparar evidencia para futuras revisiones PCI DSS, SOC 2 e ISO 27001.

Validar no persistencia de PAN/CVV, cifrado donde corresponda, gestion y rotacion de secretos, minimo privilegio, segregacion de funciones, auditoria, retencion, acceso operacional y respuesta ante incidentes.

Toda afirmacion de compliance debe etiquetarse como `READY`, `GAP` o `NOT APPLICABLE`, con evidencia y propietario.

## 16. Performance y resiliencia

Medir con datos representativos:

- latencia de casos de uso;
- throughput;
- concurrencia;
- idempotencia bajo carga;
- contention en unicidades;
- consultas e indices;
- consumer lag;
- reintentos y recuperacion;
- escalabilidad horizontal.

Los umbrales deben quedar definidos por el entorno. Si no existen umbrales aprobados, registrar la medicion como baseline y crear un riesgo, no declarar automaticamente cumplimiento.

## 17. Testing Review

Validar existencia, ejecucion y resultados de:

- unit tests;
- application tests;
- repository tests;
- API tests;
- Gateway mock tests;
- Billing tests;
- Refund tests;
- cross-tenant tests;
- idempotency tests;
- event integration tests;
- E2E tests;
- seguridad y resiliencia;
- migraciones Flyway.

No cerrar el contrato si existen pruebas criticas fallando, cobertura minima incumplida o escenarios de seguridad sin evidencia. Registrar cobertura por dominio y resultados reproducibles del pipeline.

## 18. Documentacion final

Crear en `engineering/contracts/EC-018/artifacts/final/`:

- `payment-final-architecture.md`;
- `payment-domain-review.md`;
- `payment-security-review.md`;
- `payment-event-review.md`;
- `payment-multitenant-validation.md`;
- `billing-review.md`;
- `refund-review.md`;
- `payment-api-review.md`;
- `payment-e2e-report.md`;
- `payment-closure-report.md`.

Actualizar `payment-technical-debt.md` con cada item Critical, High, Medium o Low, incluyendo descripcion, impacto, prioridad, evidencia, responsable y plan de mitigacion.

No duplicar informacion: enlazar los artefactos de las Partes 1, 2 y 3 cuando sigan vigentes.

## 19. AI Context Update

Actualizar `engineering/ai/context/` con:

- `payment-domain-context.md`;
- `billing-domain-context.md`;
- `refund-domain-context.md`;
- `payment-events-context.md`;
- `gateway-context.md`.

Cada archivo debe indicar fuente, version del contrato y decisiones vigentes. No introducir decisiones nuevas ni duplicar contenido contradictorio.

## 20. Handoff a EC-019

Crear `EC-018-HANDOFF-TO-EC019.md` con capacidades disponibles, eventos publicos, APIs autorizadas y restricciones.

EC-019 Inventory & Fulfillment debe:

- consumir eventos publicos de Order y Payments;
- no modificar Payment, Billing, Refund ni Commerce;
- no recalcular montos comerciales o financieros;
- respetar `tenantId`;
- no depender de tablas internas de EC-018;
- utilizar contratos versionados y eventos publicos.

## 21. Checklist de cierre

Marcar cada item con evidencia y estado:

- Payment Aggregate aprobado;
- PaymentAttempt y retry aprobados;
- idempotencia validada bajo repeticion y concurrencia;
- Gateway Abstraction desacoplada y sin proveedores reales;
- Billing e Invoice funcionando;
- Refund parcial y total funcionando;
- integracion asincrona con Order validada;
- eventos publicados, consumidos y recuperables;
- APIs y OpenAPI validados;
- seguridad y permisos validados;
- aislamiento multi-tenant validado;
- base de datos y Flyway validados;
- observabilidad completa;
- compliance readiness documentada;
- performance baseline documentado;
- pruebas criticas exitosas;
- documentacion completa;
- deuda tecnica registrada;
- handoff a EC-019 creado.

## 22. Definition of Done

EC-018 solo puede cerrarse cuando todos los items de la matriz y checklist tienen estado `PASS`, o existe una excepcion formal aprobada por el Architecture Governance Board.

La aprobacion requiere:

- evidencia de ejecucion end-to-end;
- evidencia de seguridad y aislamiento tenant;
- evidencia de eventos y recuperacion;
- evidencia de migraciones y persistencia;
- evidencia de pruebas criticas exitosas;
- documentos finales completos;
- deuda tecnica sin items Critical abiertos;
- decision formal, fecha, revisores y firma o registro equivalente del Board.

## 23. Resultado esperado

Si todos los gates pasan:

```text
EC-018
STATUS: APPROVED
```

Si existe cualquier gap no aprobado:

```text
EC-018
STATUS: CONDITIONAL o BLOCKED
```

La plataforma queda preparada para iniciar EC-019 Inventory & Fulfillment sin modificar las decisiones arquitectonicas aprobadas.

## FIN EC-018 PARTE 4

