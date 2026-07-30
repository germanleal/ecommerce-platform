# Microservices Architecture

Este documento define los microservicios que implementarán los Bounded Contexts del dominio sin crear código.

## Criterios

- Cada microservicio debe mapear a un bounded context existente.
- No se crean servicios técnicos sin dueño.
- Todas las responsabilidades deben estar justificadas por el dominio.

## Microservicios definidos

### 1. Provisioning Service
- **Bounded Context**: Provisioning & Tenant Management
- **Propósito**: Gestionar el ciclo de vida de los tenants, planes, suscripciones y parámetros de multi-tenant.
- **Responsabilidades**:
  - Registro y activación de tenants.
  - Asociar tenants con plans y recursos.
  - Publicar eventos de tenant (`TenantRegistered`, `TenantSuspended`, `StoreCreated`).
  - Exponer información de tenant para resolución de contexto.
- **Casos de uso soportados**:
  - UC-PROV-01: Registro de Nuevo Tenant.
  - Administración de planes y estado del tenant.
- **Aggregates administrados**:
  - `Tenant`
  - `Subscription`
  - `StoreConfiguration`
- **Eventos publicados**:
  - `TenantRegistered`
  - `TenantSuspended`
  - `StoreCreated`
- **Eventos consumidos**:
  - Ninguno en el primer alcance.

### 2. IAM Service
- **Bounded Context**: Identity & Access (IAM)
- **Propósito**: Proporcionar autenticación, autorización, roles y perfiles.
- **Responsabilidades**:
  - Verificar tokens y permisos.
  - Gestionar usuarios, roles y grupos.
  - Exponer claims y metadatos de sesión.
- **Casos de uso soportados**:
  - Autenticación de usuarios.
  - Autorización de acciones por rol y tenant.
- **Aggregates administrados**:
  - `User`
  - `Role`
  - `Permission`
  - `UserProfile`
- **Eventos publicados**:
  - `CustomerRegistered`
  - `UserRoleAssigned`
- **Eventos consumidos**:
  - `TenantRegistered` (para crear contexto RBAC inicial).

### 3. Store Operations Service
- **Bounded Context**: Store Operations (Catalog & Inventory)
- **Propósito**: Gestión de catálogo, precios y stock de cada tienda.
- **Responsabilidades**:
  - Crear/actualizar productos y variantes.
  - Publicar productos para el marketplace.
  - Controlar niveles de inventario y reservas.
- **Casos de uso soportados**:
  - UC-OPS-01: Publicar Producto.
  - Gestión de stock y categorías.
- **Aggregates administrados**:
  - `Product`
  - `SKU`
  - `InventoryItem`
  - `Category`
- **Eventos publicados**:
  - `ProductPublished`
  - `StockLevelLow`
  - `StockReserved`
- **Eventos consumidos**:
  - `TenantRegistered` (para inicializar contexto de tenant).

### 4. Order Fulfillment Service
- **Bounded Context**: Order Fulfillment
- **Propósito**: Orquestar el flujo de órdenes, checkout y fulfillment.
- **Responsabilidades**:
  - Validar pedidos y calcular totales.
  - Gestionar estados de órdenes y envío.
  - Coordinar reserva de stock y confirmación de pagos.
- **Casos de uso soportados**:
  - UC-FULL-01: Realizar Checkout.
  - Gestión de estados de pedido.
- **Aggregates administrados**:
  - `Order`
  - `LineItem`
  - `ShippingInstruction`
- **Eventos publicados**:
  - `OrderPlaced`
  - `StockReserved`
  - `OrderCancelled`
  - `OrderShipped`
- **Eventos consumidos**:
  - `ProductPublished` (para actualizar catálogo local / precios de referencia).
  - `StockReserved` (confirmación de reserva interna).
  - `PaymentApproved`

### 5. Marketplace Discovery Service
- **Bounded Context**: Marketplace Discovery
- **Propósito**: Agregar y exponer catálogo público de tiendas y productos.
- **Responsabilidades**:
  - Indexar productos publicados.
  - Gestionar búsquedas y recomendaciones.
  - Exponer vistas de catálogo global.
- **Casos de uso soportados**:
  - Búsqueda de productos y tiendas.
  - Exploración de marketplace.
- **Aggregates administrados**:
  - `Listing`
  - `SearchIndex`
- **Eventos publicados**:
  - Ninguno en el primer alcance.
- **Eventos consumidos**:
  - `ProductPublished`
  - `StoreCreated`

### 6. Payments & Billing Service
- **Bounded Context**: Payments & Billing
- **Propósito**: Procesar pagos, emitir facturas y coordinar cobros a tenants.
- **Responsabilidades**:
  - Integrar con pasarelas externas.
  - Registrar transacciones y facturación.
  - Gestionar reembolsos y estado de cobro.
- **Casos de uso soportados**:
  - Pago de pedidos.
  - Generación de facturas.
- **Aggregates administrados**:
  - `Payment`
  - `Invoice`
  - `Transaction`
- **Eventos publicados**:
  - `PaymentApproved`
  - `PaymentFailed`
- **Eventos consumidos**:
  - `OrderPlaced`
  - `OrderCancelled`

## Observaciones

- No existe un "Database Service" ni un "Notification Service" sin justificación.
- La separación responde a capacidades y responsabilidades de dominio.
- IAM es un contexto central pero su ownership está alineado con la plataforma de seguridad.
