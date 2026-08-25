# EC-004 — Inventario de arquitectura e integración

Fecha de auditoría: 2026-08-05. Alcance: `backend/*`, `shared/*`, `infra/*`, `docs/*` y contratos EC disponibles.

## Hallazgos

- Existen 28 módulos bajo `backend/`, pero no todos tienen el mismo nivel de implementación.
- `shared/` era únicamente estructura placeholder; ahora `shared/contracts` contiene `platform-shared-contracts:0.1.0`.
- Los servicios deben consumir contratos versionados; no se permite copiar DTOs de integración entre bounded contexts.
- Las integraciones síncronas pasan por API Gateway y las asíncronas por topics/eventos versionados.
- Un `pom.xml`, controller o README no es evidencia de integración funcional.

## Capacidades

| Capacidad | Servicios | Estado EC-004 |
|---|---|---|
| Identidad y tenant | identity, tenant, api-gateway | En integración; validar end-to-end |
| Catálogo y pricing | catalog, product, pricing, promotion, search | Contratos por validar |
| Carrito y checkout | cart, checkout, order, payment | Contratos por validar |
| Inventario y despacho | inventory, shipping | Contratos por validar |
| Comunicación | notification, integration | Parcial; validar proveedores |
| Administración y auditoría | administration, audit | Parcial; validar persistencia |
| Analítica y AI | analytics, ai-gateway | Parcial; sin aprobación de producción |

Cada integración sólo puede declararse lista con contrato versionado, consumidor real, prueba de compatibilidad, trazabilidad, manejo de errores y validación del despliegue.
