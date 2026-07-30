# IAM Architecture Foundation

## Flujo

React → OAuth2 Authorization Code → Keycloak → JWT → Spring Security → Application Layer → Domain.

## Roles iniciales

`PLATFORM_ADMIN`, `ADMIN`, `USER`, `GUEST`.

## Límites

Keycloak administra credenciales, sesiones, tokens y eventos de seguridad. El backend valida JWT y aplica autorización. El frontend administra el flujo de autenticación, sesión y rutas protegidas. Tenant y sus límites quedan fuera de este contrato.
