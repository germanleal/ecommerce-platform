# EC-006 Template Catalog

## Introducción

Este catálogo define todas las plantillas oficiales del proyecto para EC-006 Parte 1.

## Categorías de plantillas

### Backend
- `TPL-001-springboot-service.md`
- `TPL-002-java-library.md`
- `TPL-003-config-server.md`
- `TPL-004-api-gateway.md`
- `TPL-005-service-discovery.md`

### Frontend
- `TPL-006-react-marketplace.md`
- `TPL-007-react-backoffice.md`
- `TPL-008-react-tenant-admin.md`
- `TPL-009-shared-ui.md`
- `TPL-010-design-system.md`

### Shared
- `TPL-011-java-library.md`
- `TPL-012-typescript-library.md`
- `TPL-013-shared-contracts.md`
- `TPL-014-shared-clients.md`

### Infrastructure
- `TPL-015-docker.md`
- `TPL-016-postgresql.md`
- `TPL-017-kafka.md`
- `TPL-018-keycloak.md`
- `TPL-019-observability.md`
- `TPL-020-environment-variables.md`

### Engineering
- `TPL-021-adr.md`
- `TPL-022-engineering-contract.md`
- `TPL-023-review.md`
- `TPL-024-checklist.md`
- `TPL-025-risks.md`
- `TPL-026-metrics.md`
- `TPL-027-ia-prompt.md`
- `TPL-028-validations.md`

### Documentation
- `TPL-029-readme.md`
- `TPL-030-changelog.md`
- `TPL-031-contributing.md`
- `TPL-032-architecture.md`
- `TPL-033-domain.md`
- `TPL-034-api.md`
- `TPL-035-deployment.md`
- `TPL-036-operations.md`

## Organización física
ingineering/templates/
├── backend/
├── frontend/
├── shared/
├── infrastructure/
├── documentation/
└── engineering/

## Reglas generales de construcción
- Las plantillas deben ser uniformes y reutilizables.
- No deben incluir código de dominio productivo.
- No deben establecer configuraciones experimentales.
- Deben ser fáciles de interpretar por desarrolladores y agentes IA.

## Reglas de versionamiento
- Cada plantilla debe incluir: Identificador, Nombre, Versión, Estado, Autor, Fecha de creación, Fecha de modificación, Compatibilidad, Engineering Contract de origen.
- Los nombres de archivos deben seguir el patrón `TPL-###-description.md`.
- No se permiten nombres ambiguos.

## Gobernanza y ciclo de vida
- Todas las plantillas son artefactos oficiales.
- Ninguna plantilla puede modificarse directamente sin pasar el proceso de gobernanza.
- El ciclo de vida debe ser: Draft → Review → Approved → Deprecated → Archived.
- Solo las plantillas `Approved` pueden usarse en proyectos productivos.

## Restricciones
- No almacenar plantillas fuera de `engineering/templates/`.
- Los templates no deben definir configuraciones finales de Kafka, Keycloak o Docker Compose.
- Las plantillas deben respetar las decisiones congeladas de EC-006.
