# EC-007 — Platform runtime model

## Runtime groups

```text
Identity:       keycloak, identity-service, tenant-service
Business:       marketplace, commerce, cart, checkout, order, payment, inventory, shipping
Platform:       api-gateway, service-discovery, config-server
Messaging:      zookeeper, kafka
Data:           postgres, postgres-keycloak
Operations:     analytics, integration, administration, monitoring
```

## Startup order

1. Networks and persistent volumes.
2. PostgreSQL instances.
3. Zookeeper and Kafka.
4. Keycloak and realm configuration.
5. Platform services.
6. Business services.
7. Analytics, integrations and administration.

Compose health checks provide readiness dependencies for the infrastructure currently defined. Services not present in the root Compose file are recorded as a Part 2 gap.
