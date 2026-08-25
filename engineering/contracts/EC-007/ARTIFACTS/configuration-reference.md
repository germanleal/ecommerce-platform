# EC-007 — Configuration reference

| Variable | Purpose | Example |
|---|---|---|
| `DATABASE_URL` | JDBC connection | `jdbc:postgresql://postgres:5432/ecommerce` |
| `DATABASE_USERNAME` / `DATABASE_PASSWORD` | database credentials | environment only |
| `KAFKA_BOOTSTRAP_SERVERS` | broker address | `kafka:29092` |
| `KEYCLOAK_URL` | identity base URL | `http://keycloak:8080` |
| `KEYCLOAK_ISSUER_URI` | JWT issuer | `http://keycloak:8080/realms/platform` |
| `KEYCLOAK_REALM` | realm name | `platform` |
| `TENANT_CONFIG` | tenant propagation mode | `header-and-jwt` |
| `LOG_LEVEL` | application log level | `INFO` |

Secrets are supplied through environment or a future secret manager; they are not stored in this reference.
