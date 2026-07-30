# Communication Architecture

Este documento describe cómo los microservicios se comunican entre sí de forma síncrona y asíncrona.

## Objetivo

- Establecer patrones de comunicación.
- Definir cuándo usar llamadas HTTP y cuándo usar eventos.
- Justificar riesgos y decisiones de integración.

## Comunicación Síncrona

### Principios
- Se utiliza para validaciones que requieren respuesta inmediata.
- Debe limitarse a casos donde la consistencia es crítica y la latencia es aceptable.
- Se evita la dependencia directa siempre que exista una alternativa basada en eventos.

### Casos de uso aceptados
- `IAM` para autorización y validación de tokens.
- `Provisioning` para resolver `tenant_id` y validar estado de tenant.
- `Payments & Billing` para consulta puntual de estado de pago cuando el flujo de checkout requiere resultado inmediato.

### Justificación
- Autenticación/Autorización debe ser síncrona porque el frontend y los servicios necesitan conocer el resultado al momento.
- Resolución del tenant es un requisito transversal con baja latencia.
- El pago es un proceso de negocio donde la confirmación inmediata reduce el riesgo de inconsistencia de pedido.

### Riesgos
- Acoplamiento temporal entre servicios.
- Mayor latencia en los flujos críticos.
- Riesgo de fallas en cascada si un servicio upstream no responde.

### Mitigaciones
- Uso de circuit breakers y timeouts.
- Cache de metadatos de tenant y roles en servicios backend.
- Fallback de datos de tenant mediante eventos de dominio cuando sea posible.

## Comunicación Asíncrona

### Principios
- Los eventos de dominio son el medio principal para compartir cambios de estado entre bounded contexts.
- El objetivo es evitar integraciones por base de datos y reducir el acoplamiento directo.
- Kafka es el backbone primario de mensajería.

### Eventos de dominio clave
- `TenantRegistered` — publicado por Provisioning.
- `TenantSuspended` — publicado por Provisioning.
- `StoreCreated` — publicado por Provisioning.
- `CustomerRegistered` — publicado por IAM.
- `ProductPublished` — publicado por Store Operations.
- `StockLevelLow` — publicado por Store Operations.
- `StockReserved` — publicado por Order Fulfillment.
- `OrderPlaced` — publicado por Order Fulfillment.
- `PaymentApproved` — publicado por Payments & Billing.
- `PaymentFailed` — publicado por Payments & Billing.
- `OrderCancelled` — publicado por Order Fulfillment.
- `OrderShipped` — publicado por Order Fulfillment.

### Productores / Consumidores
- `Provisioning Service` publica eventos de tenant consumidos por `IAM`, `Store Operations` y `Order Fulfillment`.
- `IAM Service` publica `CustomerRegistered` consumido por otros contextos para onboarding de usuarios.
- `Store Operations Service` publica `ProductPublished` y `StockLevelLow` consumidos por `Marketplace Discovery` y `Order Fulfillment`.
- `Order Fulfillment Service` publica `OrderPlaced`, `StockReserved`, `OrderCancelled` y `OrderShipped`.
- `Payments & Billing Service` publica `PaymentApproved` / `PaymentFailed` consumidos por `Order Fulfillment`.

### Reglas de integración
- Se evita publicar eventos técnicos. Sólo eventos de dominio de alto nivel.
- Cada servicio es responsable de producir eventos que reflejen el estado de su agregado.
- La evolución de eventos debe ser backward compatible.
- Los consumidores no deben asumir demasiada granularidad del payload.
- No se usa integración por base de datos entre servicios.

### Riesgos
- Consistencia eventual entre catálogo, pedidos y pagos.
- Eventos perdidos si Kafka no está configurado correctamente.
- Dificultad para depurar operaciones basadas en múltiples eventos.

### Mitigaciones
- Usar patrón Outbox para garantizar que eventos se publiquen solo si la transacción local es exitosa.
- Generar eventos de compensación cuando un flujo distribuido falla.
- Registrar trazas de evento para correlación de flujo.

## Patrón Sugerido
- `Outbox Pattern` en cada servicio con persistencia local de eventos.
- `Saga` / `Process Manager` para orquestar procesos de pago y fulfillment cuando el flujo involucre varios servicios.
- `Inbox` en consumidores críticos para asegurar idempotencia.
