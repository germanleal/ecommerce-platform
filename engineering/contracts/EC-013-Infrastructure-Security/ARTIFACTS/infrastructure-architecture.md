# EC-013 Infrastructure Architecture

The operational base is local-first and containerized. PostgreSQL, a dedicated Keycloak PostgreSQL instance, Kafka/Zookeeper and Keycloak are declared in Docker Compose with explicit versions, isolated networks, dedicated volumes and health checks.
