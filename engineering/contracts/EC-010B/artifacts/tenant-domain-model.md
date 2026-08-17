# EC-010B Parte 1 — Tenant domain model

Implementado en `backend/tenant-service`:

- `Tenant`: id, name, slug, status, configuration, createdAt y updatedAt.
- `TenantStatus`: `CREATED`, `ACTIVE`, `SUSPENDED`, `DEACTIVATED`.
- `Organization`: relación uno-a-uno con tenant, razón legal y propiedades comerciales.
- `TenantMember`: userId, tenantId, role, status y createdAt.
- `TenantConfiguration`: valores de configuración inmutables.

Las invariantes de transición se aplican dentro del agregado `Tenant`; no dependen del controlador.
