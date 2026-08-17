# Security Review

JWT/TenantContext está configurado y las consultas filtran tenant. **Bloqueador:** los permisos `INTEGRATION_READ`, `CONNECTOR_ADMIN`, `SYNCHRONIZATION_EXECUTE` e `INTEGRATION_AUDIT` no están aplicados mediante method security ni probados con Keycloak real.
