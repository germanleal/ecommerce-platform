# Multi-Tenant Security Review

- Identity y Tenant permanecen separados.
- Keycloak/EC-010A conserva la responsabilidad de identidad.
- `TenantAccessValidator` requiere autenticación, relación UserTenant y permiso.
- `PLATFORM_ADMIN` es la única excepción global controlada.
- El contexto no se deriva de parámetros públicos.

Pendiente: pruebas automatizadas de IDOR, enumeración, escalación y acceso cross-tenant sobre PostgreSQL real.
