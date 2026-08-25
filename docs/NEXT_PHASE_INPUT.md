# NEXT_PHASE_INPUT: Transition to EC-004

## Introducción
Este documento marca la transición desde el modelado de dominio (Fase 1/EC-003) hacia el diseño técnico e integración (Fase 2/EC-004).

## Estado de la Gobernanza
- **Contratos Cerrados**: EC-000, EC-001, EC-002, EC-003.
- **Contrato Entrante**: EC-004-Architecture-Integration.

## Artefactos Vinculantes (Inputs para EC-004)
1. **Modelo Táctico**: Agregados y Casos de Uso definidos en `docs/domain/`.
2. **Mapa de Contextos**: Relaciones estratégicas definidas en `docs/domain/09-context-map.md`.
3. **Engineering Handbook**: Estándares de codificación y diseño.

## Decisiones Congeladas para EC-004
- Uso de **OpenAPI 3.0** para APIs REST.
- Uso de **AsyncAPI** para eventos Kafka.
- Patrón **Anti-Corruption Layer (ACL)** para comunicaciones Downstream.

## Instrucciones para el Agente (EC-004)
Al iniciar la siguiente tarea, el agente debe declarar que está trabajando bajo el **Engineering Contract EC-004** y validar los artefactos de salida de EC-003 antes de proponer cualquier contrato de API.
