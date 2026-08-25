# Engineering Contract: EC-010A-Identity-Access-Management

## Información General

- **ID**: EC-010A
- **Nombre**: Identity & Access Management Foundation
- **Versión**: 1.0.0
- **Estado**: APPROVED FOR IMPLEMENTATION
- **Categoría**: Security / Identity Platform
- **Dominio**: Platform Core
- **Dependencias**: EC-004, EC-005, EC-006, EC-007, EC-008, EC-009
- **Siguiente contrato**: EC-010B Multi-Tenant Foundation

## Alcance

Implementar la plataforma IAM con Keycloak, OpenID Connect, OAuth2, JWT, Spring Security y React. Incluye identidad, autenticación, autorización, roles, permisos, sesiones, logout, refresh tokens, auditoría e integración backend/frontend.

## Fuera de alcance

Multi-tenancy, empresas, tiendas, productos, catálogo, clientes, pedidos, pagos, inventario y dominios comerciales.

## Decisiones congeladas

- Keycloak como Identity Provider.
- OpenID Connect y OAuth2.
- JWT como intercambio de identidad.
- Spring Security para backend.
- React para frontend.
- PostgreSQL y Docker Compose.

## Reglas arquitectónicas

Identity Service es un bounded context propio. Las credenciales permanecen en Keycloak; no se crearán autenticación propia, sesiones propias ni tablas de usuarios de negocio. Tenant será responsabilidad de EC-010B.
