# Connector Model

Un Connector representa una configuración aislada por `tenantId` para un sistema externo. Incluye `connectorId`, `providerType`, `version`, capabilities, endpoint lógico, referencia a secreto, estado y timestamps.

## Lifecycle

`REGISTERED -> CONFIGURED -> ACTIVE -> SUSPENDED -> RETIRED`.

Las credenciales se almacenan fuera del dominio y nunca en payloads o logs. Cada cambio de configuración genera auditoría y requiere versionado compatible.
