# EC-007 — Runtime architecture

```text
Host
 ├─ frontend/backend-network
 ├─ backend services
 ├─ identity-network ─ keycloak ─ postgres-keycloak
 ├─ messaging-network ─ kafka ─ zookeeper
 └─ database-network ─ postgres
```

Compose service names are the internal DNS names. Applications use `postgres:5432`, `kafka:29092` and `keycloak:8080` inside the network; host ports are for local clients only.
