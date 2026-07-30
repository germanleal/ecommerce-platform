# Engineering Contract: EC-003-Tactical-DDD

## 1. Información General
- **ID**: EC-003
- **Nombre**: Tactical Domain Driven Design
- **Versión**: 1.0.0
- **Estado**: APPROVED
- **Dependencias**: EC-000, EC-001, EC-002
- **Consumidores**: EC-004, EC-005, EC-006

## 2. Objetivo
Transformar el modelo estratégico (EC-002) en un modelo táctico (Aggregates, Entities, VOs) completamente documentado y agnóstico a la tecnología, que sirva como plano definitivo para la implementación.

## 3. Contexto
Tras definir los límites de los contextos en EC-002, es necesario detallar la lógica interna y las invariantes de negocio para asegurar que el software refleje fielmente la realidad del mall virtual.

## 4. Alcance
- **Incluye**: Modelado táctico de todos los Bounded Contexts, definición de invariantes, casos de uso detallados y matriz de trazabilidad.
- **No incluye**: Implementación de código, diseño de APIs o bases de datos.

## 5. Entradas Obligatorias
- Engineering Handbook (EC-000).
- Product Discovery (EC-001).
- Strategic DDD / Context Map (EC-002).

## 6. Resultados y Artefactos
Todos los artefactos de diseño táctico se encuentran en el directorio `artifacts/`, organizados por tipo de patrón DDD.

## 7. Definition of Done
- [x] Todos los Bounded Contexts poseen modelo táctico.
- [x] Invariantes de negocio documentadas por agregado.
- [x] Matriz de trazabilidad Capability -> Use Case -> Aggregate completa.
- [x] Revisión arquitectónica ejecutada y riesgos identificados.
- [x] Modelo 100% independiente de tecnología.
