# System Context

Este documento describe el contexto general de la plataforma, sus actores y las relaciones entre los bounded contexts técnicos.

## Objetivo

Definir el sistema como un conjunto de componentes de negocio y sus dependencias principales en la plataforma SaaS multi-tenant.

## Alcance

- Bounded Contexts técnicos y su correspondencia con los dominios de EC-002 y EC-003.
- Actores externos e internos.
- Puntos de integración principales.

## Contexto del Sistema

La plataforma es un ecosistema multi-tenant de comercio electrónico tipo mall, donde:

- Cada `Tenant` representa una empresa que gestiona una o más `Stores`.
- Cada `Store` publica catálogo y vende productos a `Customers`.
- Un marketplace global permite descubrir tiendas y productos de múltiples tenants.
- La seguridad y el acceso son gestionados de forma centralizada.

## Actores Principales

- Platform Admin: administra la plataforma, tenants y políticas globales.
- Tenant Admin: configura tenant, tiendas y catálogos.
- Store Manager: opera catálogo, stock y órdenes dentro de una tienda.
- Customer: compra en tiendas dentro del marketplace.
- Guest: navega catálogo público y marketplace.

## Bounded Contexts Técnicos

- `Provisioning & Tenant Management`
- `Marketplace Discovery`
- `Store Operations`
- `Order Fulfillment`
- `Identity & Access (IAM)`
- `Payments & Billing`

## Principales Relaciones

- `IAM` proporciona identidad y autorización a todos los demás contextos.
- `Provisioning` define la existencia de tenants y expone el tenant_id activo.
- `Store Operations` es el dueño del catálogo y del estado de stock.
- `Order Fulfillment` consume datos de catálogo y coordina con pagos.
- `Marketplace Discovery` consume producto publicado como vista global.
- `Payments & Billing` procesa transacciones y genera estados de pago.

## Mapa de Contextos Técnicos

```mermaid
flowchart LR
    IAM[Identity & Access (IAM)]
    PROV[Provisioning & Tenant Management]
    OPS[Store Operations]
    FULL[Order Fulfillment]
    DISC[Marketplace Discovery]
    PAY[Payments & Billing]

    IAM -->|auth / token| PROV
    IAM -->|auth / token| OPS
    IAM -->|auth / token| FULL
    IAM -->|auth / token| DISC
    IAM -->|auth / token| PAY

    PROV -->|tenant metadata| OPS
    PROV -->|tenant metadata| FULL

    OPS -->|catalog & stock events| FULL
    OPS -->|published catalog view| DISC

    FULL -->|payment request| PAY
    PAY -->|payment status| FULL
```

## Notas

- No se definen componentes técnicos sin propósito. Cada microservicio corresponde a un bounded context de negocio.
- La comunicación se divide en síncrona para validaciones puntuales y asíncrona para integración de datos entre contextos.
