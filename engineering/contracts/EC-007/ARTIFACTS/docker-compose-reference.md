# EC-007 — Docker Compose reference

## Start

```powershell
docker compose up -d --build
```

The base runtime starts PostgreSQL, Keycloak PostgreSQL, Zookeeper, Kafka, `kafka-init`, Keycloak and the currently containerized application services. `kafka-init` is a one-shot job that creates the event topics and exits successfully.

## Persistence

Named volumes: `postgres-data`, `keycloak-postgres-data`, `kafka-data`, `zookeeper-data`, `zookeeper-logs`. PostgreSQL local initialization is mounted read-only from `infrastructure/postgres/init`.

## Networks

`database-network`, `messaging-network`, `identity-network` and `backend-network` are used to limit service communication. Host ports are exposed only for local development.
