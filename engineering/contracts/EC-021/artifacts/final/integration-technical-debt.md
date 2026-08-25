# Integration Technical Debt

## Critical

- No existe suite de integración real con Kafka, PostgreSQL y Keycloak. Impacto: no se demuestra E2E, resiliencia ni aislamiento. Prioridad inmediata. Remediación: Testcontainers y pruebas cross-tenant.
- Retry queue no tiene worker completo de ejecución/DLQ/replay. Impacto: mensajes pueden quedar pendientes. Prioridad inmediata. Remediación: RetryWorker, límite, DLQ y replay auditado.

## High

- Permisos específicos no están aplicados. Impacto: autorización insuficiente. Remediación: `@PreAuthorize`/method security y pruebas de claims.
- Métricas y tracing no implementados. Impacto: observabilidad insuficiente. Remediación: Micrometer/OpenTelemetry y correlation propagation.

## Medium

- Scheduler actualiza registros vencidos, pero no ejecuta jobs por tenant. Impacto: sincronización programada incompleta. Remediación: principal de sistema seguro y dispatcher tenant-aware.

## Low

- Adapters mock comparten lógica mínima y no validan contratos externos. Impacto bajo mientras proveedores reales estén fuera de alcance. Remediación: contract tests de adapters.
