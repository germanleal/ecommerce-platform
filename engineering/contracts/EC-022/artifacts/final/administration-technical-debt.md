# Administration Technical Debt

## Critical

- No existe validación E2E con Keycloak, PostgreSQL, Integration, Analytics y BackOffice. Impacto: no se garantiza operación completa. Prioridad inmediata. Remediación: entorno integrado y pruebas automatizadas.
- User Administration y Audit Center no tienen APIs completas verificadas. Impacto: capacidades declaradas no están cerradas. Remediación: endpoints, repositorios, DTOs y pruebas.

## High

- Permisos efectivos no están aplicados por endpoint ni ocultados con guards reales en frontend. Impacto: riesgo de autorización. Remediación: method security, claims y permission-aware routes.
- Frontend no tiene build/test ejecutado por falta de dependencias instaladas. Impacto: UI no verificable. Remediación: `npm.cmd install`, build y suite Vitest.

## Medium

- Búsqueda, filtros y paginación son incompletos. Impacto: backoffice limitado. Remediación: query DTOs y componentes reutilizables.

## Low

- Módulos de auditoría y settings muestran placeholders cuando el API no está disponible. Impacto: experiencia degradada. Remediación: endpoints y estados de carga/error específicos.
