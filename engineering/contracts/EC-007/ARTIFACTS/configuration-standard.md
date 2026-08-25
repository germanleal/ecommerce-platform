# EC-007 — Configuration standard

- Environment variables are the source of runtime configuration.
- `.env.example` and `configuration/platform.env.example` contain placeholders only.
- Required cross-service variables: `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`, `KAFKA_URL`, `KAFKA_BOOTSTRAP_SERVERS`, `KEYCLOAK_URL`, `KEYCLOAK_ISSUER_URI`, `TENANT_CONFIG`, `LOG_LEVEL`.
- Profiles are `dev`, `test`, and `prod`; production secrets must come from a secret manager.
- `DATABASE_PASSWORD`, Keycloak admin credentials and tokens must never be committed.
