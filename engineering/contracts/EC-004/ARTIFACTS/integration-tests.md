# EC-004 Parte 3 — Pruebas de integración

## Validado

Los siguientes módulos ejecutaron `mvn -q "-Dproject.build.directory=$PWD/build-output" test` con resultado exitoso:

- `backend/order-service`
- `backend/inventory-service`
- `backend/analytics-service`
- `backend/integration-service`

Estas pruebas son unitarias/integración de módulo; no prueban Kafka, PostgreSQL real ni el flujo completo.

## Pendiente para READY FOR ARCHITECTURE GOVERNANCE REVIEW

- Testcontainers con Kafka y PostgreSQL.
- Productor/consumidor por cada evento del mapa.
- Flujo E2E Tenant → User → Product → Order → Payment → Inventory → Analytics.
- Prueba negativa de aislamiento Tenant A/Tenant B y JWT/scopes.
