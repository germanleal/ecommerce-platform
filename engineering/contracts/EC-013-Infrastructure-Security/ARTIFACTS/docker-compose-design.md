# Docker Compose Design

Compose is the single entry point. Startup order is PostgreSQL → Keycloak and Zookeeper → Kafka. Backend and frontend workloads remain attachable through the reserved networks without being introduced as undeclared services.
