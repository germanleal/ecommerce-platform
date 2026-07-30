# Matriz de Dependencias de Fases

## Introducción
Este documento detalla las dependencias, entradas y salidas de cada fase del proyecto para asegurar un flujo de construcción incremental y coherente.

| Fase | Nombre | Entradas | Salidas / Artefactos | Dependencias |
| :--- | :--- | :--- | :--- | :--- |
| **0** | Foundation | Visión del Producto | Engineering Handbook, Repository Structure | Ninguna |
| **1** | DDD Strategic Design | Fase 0, Requerimientos | Capability Map, Bounded Contexts, Context Map | Fase 0 |
| **2** | DDD Tactical Design | Fase 1 | Aggregates, Entities, VOs, Domain Services, Use Cases | Fase 1 |
| **3** | Architecture Integration | Fase 2 | Contratos OpenAPI/AsyncAPI, Estrategias de Integración | Fase 2 |
| **4** | Repository Architecture | Fase 3 | Esquemas BD, Estrategia Multi-Tenant de Persistencia | Fase 3 |
| **5** | Engineering Templates | Fase 4 | Arquetipos, Generadores de Código | Fase 4 |
| **6** | Platform Bootstrap | Fase 5 | Repositorios inicializados, Pipelines base | Fase 5 |
| **7** | Infrastructure & Security | Fase 6 | Keycloak, Kafka, Postgres, API Gateway configurados | Fase 6 |
| **8** | Marketplace & Storefront | Fase 7 | Catálogo funcional, Carrito, Checkout | Fase 7 |
| **9** | AI Integration | Fase 8 | Modelos de IA integrados (Recomendadores, etc.) | Fase 8 |

## Análisis de Riesgos por Transición

- **Fase 1 -> 2**: Riesgo de ambigüedad en los límites de contexto. Mitigación: Revisión estratégica antes de iniciar diseño táctico.
- **Fase 2 -> 3**: Riesgo de contratos inconsistentes con el dominio. Mitigación: Trazabilidad obligatoria Use Case -> API.
- **Fase 5 -> 6**: Riesgo de plantillas no escalables. Mitigación: Pruebas de carga iniciales sobre arquetipos.
