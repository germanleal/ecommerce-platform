# Analytics Technical Debt

## Critical

- Proyecciones de ventas, refunds y fulfillment no están completas desde eventos. Impacto: KPIs y reportes pueden ser incompletos. Prioridad inmediata. Remediación: implementar projection services y pruebas de replay.
- No existe suite de integración Kafka/PostgreSQL/Keycloak. Impacto: no se puede demostrar consistencia ni aislamiento. Prioridad inmediata. Remediación: Testcontainers y pruebas cross-tenant.

## High

- Falta `analytics_dashboard` y observabilidad real (métricas/tracing). Impacto: incumplimiento de producción. Remediación: migración, Micrometer y propagación de correlationId.
- Permisos específicos no están aplicados. Impacto: autorización insuficiente. Remediación: method security y pruebas JWT por permiso.

## Medium

- APIs devuelven `Map` en lugar de DTOs versionados. Impacto: contrato débil. Remediación: DTOs y OpenAPI.

## Low

- Cache local no distribuida. Impacto: comportamiento divergente en múltiples réplicas. Remediación: adoptar cache compartida cuando la plataforma lo habilite.
