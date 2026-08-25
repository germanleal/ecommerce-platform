# Security Architecture

Este documento describe la estrategia de seguridad global y cómo se integra con los servicios y el modelo multi-tenant.

## Objetivo

- Definir la seguridad a nivel de arquitectura.
- Describir mecanismos de autenticación, autorización y separación de roles.
- Alinear la seguridad con Keycloak, OAuth2 y OpenID Connect.

## Principios

- Security by Design: la seguridad es una capa transversal.
- Autenticación centralizada con Keycloak.
- Autorización basada en roles, permisos y claims de tenant.
- Separación clara entre plataforma, tenant y tienda.
- Minimizar la exposición de datos sensibles entre servicios.

## Modelo de seguridad

- `Platform Admin`: control total sobre tenants y configuración global.
- `Tenant Admin`: gestión de stores y configuración propia del tenant.
- `Store Manager`: operación de catálogo, inventario y pedidos dentro de una tienda.
- `Customer`: compra en tiendas, visualiza su propio historial.
- `Guest`: navegación pública sin información privada.

## Capas de protección

1. **Frontend**: maneja login, token refresh y autorización de rutas.
2. **API Gateway**: valida tokens JWT, aplica políticas de acceso y enruta peticiones.
3. **Microservicios**: validan roles y tenant_id en cada request.
4. **Kafka**: eventos transportan claims de tenant y correlación para auditoría.
5. **PostgreSQL**: datos aislados por esquema o por columna con tenant_id.

## Tokens y claims

- `access_token` OpenID Connect para APIs.
- `id_token` para información del usuario autenticado.
- Claims principales:
  - `sub`
  - `preferred_username`
  - `email`
  - `roles`
  - `tenant_id`
  - `store_id` (si aplica)
  - `tenant_role`

## Autorización

- RBAC con roles globales y roles de tenant.
- Cada llamada se valida contra los claims emitidos por Keycloak.
- Los servicios confían en el gateway para la primera capa, pero también realizan validación propia.
- Autorización de datos: `tenant_id` obligatorio en cada request para filtrar recursos.

## Protección de la superficie

- Uso de HTTPS en todas las comunicaciones internas y externas.
- Validación de scopes y audiences en los tokens.
- Separación de grupos de Keycloak para administrator, manager y customer.
- Auditoría de accesos críticos y cambios de configuración.

## Integración con eventos

- Los eventos de Kafka deben encapsular `tenantId` para garantizar el contexto correcto.
- No se debe exponer información de usuarios dentro de eventos salvo identificadores necesarios.
- El consumidor debe validar que el evento pertenece al tenant adecuado antes de aplicar cambios.
