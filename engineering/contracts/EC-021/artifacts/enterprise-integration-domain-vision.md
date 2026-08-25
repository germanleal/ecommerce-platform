# EC-021 Enterprise Integration — Domain Vision

Enterprise Integrations desacopla la plataforma de sistemas externos mediante Ports & Adapters, eventos públicos y APIs públicas. Posee el ciclo de vida de integraciones, conectores, sincronizaciones, errores y auditoría; no posee reglas de negocio de Commerce, Orders, Payments, Inventory o Analytics.

## Capacidades

- Registro y versionado de conectores.
- Adaptación y transformación de payloads.
- Sincronización inbound, outbound, batch y event-driven.
- Retry, timeout, circuit breaker y dead-letter handling.
- Auditoría por tenant y proveedor.

## Exclusiones

No se implementan proveedores reales en esta fase ni se modifican dominios fuente.
