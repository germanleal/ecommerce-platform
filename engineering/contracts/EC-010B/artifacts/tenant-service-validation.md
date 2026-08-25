# Tenant service validation

Validación ejecutada el 2026-08-06:

- `platform-shared-contracts`: 4 tests, BUILD SUCCESS.
- `tenant-service`: 4 tests, BUILD SUCCESS.
- `docker compose config --quiet`: pendiente de ejecución después de añadir el servicio.

El servicio expone `POST /api/v1/tenants`, consulta por id y transiciones de lifecycle en el puerto 8098. Flyway aplica `V1__create_tenant_foundation.sql` y `V2__tenant_organization_and_events.sql`.
