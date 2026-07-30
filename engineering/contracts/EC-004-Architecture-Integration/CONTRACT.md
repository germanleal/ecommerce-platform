# Engineering Contract: EC-004-Architecture-Integration

## Información General
- **ID**: EC-004
- **Nombre**: Architecture Integration & Contracts
- **Versión**: 1.0.0
- **Estado**: PENDIENTE
- **Autor**: Architecture Governance Board (AGB)
- **Dependencias**: EC-003

## Objetivo
Definir los contratos de comunicación (APIs y Eventos) entre los Bounded Contexts basados en el modelo de dominio.

## Alcance
- **Incluye**: OpenAPI Specs, AsyncAPI Specs, Estrategias de Consistencia Eventual.
- **No incluye**: Implementación de servicios.

## Entradas Obligatorias
- EC-003: Tactical DDD (Use Cases & Events).

## Actividades
1. **Diseño de APIs (OpenAPI)**: Definir endpoints y DTOs basados en casos de uso.
2. **Diseño de Eventos (AsyncAPI)**: Definir esquemas de eventos para Kafka.
3. **Estrategia de Integración**: Definir patrones (Saga, Inbox/Outbox).

## Artefactos de Salida
- Especificaciones OpenAPI.
- Especificaciones AsyncAPI.

## Definition of Done
- [ ] Todas las APIs de negocio documentadas.
- [ ] Todos los eventos asíncronos con esquema definido.
- [ ] Estrategia de consistencia validada por arquitectura.
