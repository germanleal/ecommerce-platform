# Kafka Architecture

Este documento define la estrategia de Kafka para la plataforma, basada en eventos de dominio y ownership claro.

## Objetivo

- Definir la forma en que Kafka soportará la integración asíncrona.
- Establecer responsabilidad sobre la publicación y consumo de eventos.
- Describir la evolución de eventos sin acoplamiento fuerte.

## Principios

- Kafka se usa para eventos de dominio, no para datos técnicos ni sincronización de estado.
- Cada evento tiene un dueño claro: el microservicio que conoce la semántica y garantiza la validez.
- Se evita la integración por base de datos directa entre servicios.
- Se mantiene la consistencia eventual con procesamiento idempotente.

## Ownership de eventos

- `Provisioning Service` es dueño de `TenantRegistered`, `TenantSuspended`, `StoreCreated`.
- `IAM Service` es dueño de `CustomerRegistered`, `UserRoleAssigned`.
- `Store Operations Service` es dueño de `ProductPublished`, `StockLevelLow`, `StockReserved`.
- `Order Fulfillment Service` es dueño de `OrderPlaced`, `OrderCancelled`, `OrderShipped`.
- `Payments & Billing Service` es dueño de `PaymentApproved`, `PaymentFailed`.

## Estrategia de publicación

- Publicar eventos cuando cambie el estado de un agregado de negocio.
- No publicar eventos intermedios de workflow.
- Cada servicio debe asegurar transaccionalidad entre su base de datos y la publicación de eventos mediante Outbox.
- Evitar incluir información sensible o redundante. Publicar mínimos datos necesarios para consumir.

## Estrategia de consumo

- Los consumidores deben ser idempotentes y capaces de procesar eventos duplicados.
- No deben realizar actualizaciones que dependan de eventos antiguos sin validar la versión del agregado.
- Se prefiere rehidratación de vistas locales sobre llamadas síncronas a servicios remotos.

## Evolución de eventos

- Mantener compatibilidad hacia atrás al agregar campos opcionales.
- No eliminar campos de eventos existentes en primera instancia.
- Mejorar los eventos agregando versiones semánticas en la documentación, no en el nombre del evento a menos que el payload cambie radicalmente.
- Documentar cambios en el contrato de eventos en un ADR.

## Patrones recomendados

- Outbox Pattern en cada servicio para grabar eventos junto con la transacción de la base de datos.
- Inbox Pattern para procesar eventos externos con control de duplicados.
- Saga / Process Manager para orquestar pagos y fulfillment.
- Event Sourcing no es obligatorio; se adopta solo si es necesario para trazabilidad avanzada.

## Consideraciones de Kafka local

- En el entorno local se utilizará Kafka con particiones limitadas y retención reducida.
- Los topics se deben definir en la fase de implementación, pero la arquitectura ya establece ownership y propósito.
- No se definen topics reales ni esquemas técnicos en este nivel.
