# Changelog

## 1.0.0

- Definido Commerce Context y su separación de Marketplace.
- Identificadas capacidades, actores, agregados, reglas, eventos y seguridad.
# 2026-07-30 — Completion iteration

- Completed persistent JDBC adapters, Flyway-backed mappings and tenant-scoped repository access.
- Completed Kafka event publication using the existing commerce event envelope.
- Integrated Keycloak resource-server authentication and JWT tenant context.
- Completed Shopping Cart REST commands and added tenant-isolation validation.
- EC-016 marked `APPROVED` after `mvn -q test` passed.
