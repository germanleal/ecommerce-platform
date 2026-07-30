# Engineering Contract: EC-010B-Multi-Tenant-Foundation

## Información General

- **ID**: EC-010B
- **Nombre**: Multi-Tenant Foundation
- **Versión**: 1.0.0
- **Estado**: APPROVED FOR IMPLEMENTATION
- **Dependencias**: EC-004, EC-005, EC-006, EC-007, EC-008, EC-009, EC-010A

## Alcance de la Parte 1

Definir Tenant y Store como bounded contexts separados, la estrategia shared database/shared schema con `tenant_id`, Tenant Context, Tenant Resolver, relación UserTenant y autorización contextual.

## Fuera de alcance

Autenticación, usuarios locales, contraseñas, productos, catálogo, pedidos, pagos y cualquier dominio comercial.
