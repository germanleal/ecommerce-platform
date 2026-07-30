# Marketplace Domain Vision

## Propósito

Permitir que tenants administren tiendas y ofrezcan catálogos y productos a clientes mediante una experiencia marketplace coherente.

## Actores

Platform Administrator, Tenant Administrator, Store Manager y Customer.

## Reglas principales

- Toda entidad pertenece a un `tenantId`.
- Toda Store pertenece a un Tenant.
- Un producto pertenece a una Store y se publica mediante un lifecycle explícito.
- Marketplace no administra identidad, pagos, logística ni facturación.
