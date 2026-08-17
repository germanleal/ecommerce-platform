# EC-007 Parte 3 — Service configuration standard

Cada servicio recibe infraestructura por variables de entorno y selecciona perfil mediante `SPRING_PROFILES_ACTIVE`. No se usan `localhost` ni IPs fijas dentro del Compose; los nombres DNS son `postgres`, `kafka` y `keycloak`.

Variables mínimas: `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`, `KAFKA_BOOTSTRAP_SERVERS`, `KEYCLOAK_ISSUER_URI`, `TENANT_CONFIG`, `LOG_LEVEL`.
