# EC-007 — Docker runtime standard

The current Compose foundation defines PostgreSQL, Keycloak PostgreSQL, Zookeeper, Kafka, Keycloak, Kafka topic initialization, Inventory, Analytics, Integrations, Administration and AI Gateway with networks, named volumes and health checks. PostgreSQL initialization sets UTC; Keycloak imports `infrastructure/keycloak/realm-platform.json`; Kafka creates the initial event topics through `kafka-init`.

Validation command:

```powershell
docker compose config --quiet
```

The full service bootstrap, backend service dependencies and readiness validation remain the scope of EC-007 Parte 2.
