# Keycloak Architecture

Este documento define la integración de Keycloak con la plataforma sin crear configuración real.

## Objetivo

- Establecer la estrategia de realms, clientes, roles y grupos.
- Alinear Keycloak con la arquitectura de multi-tenant y seguridad global.
- Evitar problemas comunes de modelado de identidad.

## Principios

- Keycloak es la autoridad de identidad para autenticación y autorización.
- Se usa OAuth2/OpenID Connect para el frontend y para la comunicación entre servicios.
- La estrategia de realm y clientes se diseña para soportar tenants y marketplace.

## Strategy

### Realm Strategy
- `platform-realm`: realm principal que contiene usuarios globales, tenants y clientes de la plataforma.
- Alternativa: un realm por tenant es posible, pero aumenta la complejidad de administración y no es necesaria en esta fase.
- Se adopta un único realm con separación de clientes y grupos para facilitar el onboarding y mantenimiento.

### Client Strategy
- `frontend-client`: cliente público del frontend React.
- `api-gateway-client`: cliente confidencial usado por API Gateway o proxy interno.
- `service-account-clients`: clientes de servicio para backend cuando se requieren tokens de máquina a máquina.

### Roles
- `platform_admin`
- `tenant_admin`
- `store_manager`
- `customer`
- `guest`
- `billing_manager`
- `support_user`

### Groups
- `platform-team`
- `tenant-admins`
- `store-operators`
- `customers`
- `billing-operators`

## Identity Model

- Los usuarios pertenecen a un tenant mediante el claim `tenant_id`.
- Un `Tenant Admin` puede pertenecer a grupos que le otorgan permisos específicos sobre un tenant.
- Roles y permisos se combinan en claims emitidos en el token.

## Token Flow

1. El user inicia sesión en el frontend.
2. Keycloak valida credenciales y emite `id_token` y `access_token`.
3. El frontend usa `access_token` para llamar a API Gateway.
4. El API Gateway valida el token y enruta la petición al microservicio correspondiente.
5. Cada microservicio valida los claims necesarios y aplica reglas de autorización.

## Tenant Authorization Model

- `tenant_id` es obligatorio en el token para cualquier operación de rango de tenant.
- Los tokens de `platform_admin` pueden operar sobre múltiples tenants.
- Los tokens de `tenant_admin`, `store_manager` y `customer` se limitan al tenant y/o tienda asociada.
- Los servicios deben rechazar requests con `tenant_id` ausente o inconsistente.

## Consideraciones comunes

- Evitar crear demasiados realms para no duplicar configuraciones.
- Usar roles como claims de token en lugar de depender sólo de grupos.
- No usar el token como fuente de verdad de datos transaccionales.
- Para operaciones sensibles, el servicio backend valida estado del tenant en `Provisioning`.
