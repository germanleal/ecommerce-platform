# EC-007 — Service startup order

1. Docker networks and volumes.
2. PostgreSQL and Keycloak PostgreSQL health checks.
3. Zookeeper health check.
4. Kafka health check.
5. `kafka-init` topic creation.
6. Keycloak realm import and health check.
7. Spring Boot application services.

Compose `depends_on` uses health conditions for the infrastructure services currently defined. Application-level readiness remains verified by Actuator in services configured for the runtime.
