# EC-007 Parte 3 — Startup sequence

PostgreSQL → Zookeeper → Kafka → kafka-init → Keycloak → Spring Boot applications → frontend.

La secuencia de infraestructura está expresada con `depends_on` y health conditions en Compose. La readiness de las aplicaciones se expone con Actuator donde el servicio está normalizado.
