# EC-009 Infrastructure Traceability Matrix

| Contract | Component | Docker concern | Responsibility |
|---|---|---|---|
| EC-009 | PostgreSQL | `postgres-data`, database network | Persistent storage foundation |
| EC-009 | Kafka | `kafka-data`, messaging network | Event transport foundation |
| EC-009 | Keycloak | `keycloak-data`, identity network | Identity foundation placeholder |
| EC-009 | Observability | Prometheus, Grafana, Loki, Tempo, OTEL | Logs, metrics and tracing foundation |
| EC-009 | Storage | MinIO and storage network | Object storage foundation |
| EC-009 | Delivery | Dockerfiles and Compose | Reproducible local execution |
