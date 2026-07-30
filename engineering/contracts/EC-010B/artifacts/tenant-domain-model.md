# Tenant Domain Model

## Tenant

`Tenant` es Aggregate Root y representa una empresa/organización propietaria de datos.

Campos: `id` UUID, `name`, `slug`, `status`, `configuration`, `createdAt`, `updatedAt`.

Estados: `PENDING`, `ACTIVE`, `SUSPENDED`, `INACTIVE`, `DELETED`.

## Store

`Store` es Aggregate Root independiente y siempre requiere `tenantId`. No existe Store sin Tenant.

Campos: `id` UUID, `tenantId`, `name`, `slug`, `status`, `configuration`, `createdAt`, `updatedAt`.
