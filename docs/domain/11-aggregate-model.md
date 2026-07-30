# 11. Aggregate Model

## Objetivo
Identificar y definir los Aggregate Roots (Raíces de Agregado) para cada Bounded Context, estableciendo sus límites de consistencia y responsabilidades.

## 1. Contexto: Provisioning & Tenant Management

### Aggregate Root: Tenant
- **Propósito**: Representar la identidad y estado de una organización cliente en la plataforma.
- **Responsabilidad**: Gestionar el ciclo de vida de la empresa, sus planes y el aislamiento global.
- **Invariantes**:
    - Un Tenant debe tener al menos un administrador activo.
    - El `tenantId` es inmutable una vez creado.
- **Estado**: PENDING_APPROVAL, ACTIVE, SUSPENDED, TERMINATED.
- **Eventos que genera**: `TenantRegistered`, `TenantSuspended`, `TenantPlanChanged`.
- **Casos de Uso**: Registro de Empresa, Cambio de Plan, Suspensión de Servicio.

---

## 2. Contexto: Store Operations (Catalog & Inventory)

### Aggregate Root: Store
- **Propósito**: Representar una unidad de venta independiente perteneciente a un Tenant.
- **Responsabilidad**: Orquestar la configuración de la tienda y su catálogo.
- **Invariantes**:
    - Una tienda siempre pertenece a un único Tenant.
    - El dominio de la tienda debe ser único en la plataforma.
- **Eventos que genera**: `StoreCreated`, `StoreBrandingUpdated`.

### Aggregate Root: Product
- **Propósito**: Definir la oferta comercial de un item.
- **Responsabilidad**: Gestionar la información, variantes y precios de un producto.
- **Invariantes**:
    - Un producto debe tener al menos una variante activa para ser publicado.
    - Los precios no pueden ser negativos.
- **Eventos que genera**: `ProductPublished`, `PriceUpdated`.

### Aggregate Root: InventoryItem
- **Propósito**: Controlar la disponibilidad física de un SKU.
- **Responsabilidad**: Gestionar stock, reservas y alertas.
- **Invariantes**:
    - El stock disponible no puede ser menor a cero (a menos que se permita sobreventa explícita).
- **Eventos que genera**: `StockReserved`, `StockLevelLow`, `StockReplenished`.

---

## 3. Contexto: Order Fulfillment

### Aggregate Root: Order
- **Propósito**: Representar el contrato transaccional entre un Cliente y una Tienda.
- **Responsabilidad**: Gestionar el ciclo de vida de la venta y asegurar la consistencia del pedido.
- **Invariantes**:
    - Una orden debe tener al menos un LineItem.
    - El total de la orden debe ser la suma de los LineItems más impuestos y envío.
    - No se puede cambiar el estado a SHIPPED si no ha sido PAID.
- **Estado**: CREATED, AWAITING_PAYMENT, PAID, PREPARING, SHIPPED, DELIVERED, CANCELLED.
- **Eventos que genera**: `OrderPlaced`, `OrderPaid`, `OrderCancelled`, `OrderShipped`.

---

## 4. Contexto: Identity & Access (IAM)

### Aggregate Root: User
- **Propósito**: Identidad de una persona en el sistema.
- **Responsabilidad**: Gestión de credenciales, perfiles y pertenencia a Tenants.
- **Invariantes**:
    - El email debe ser único (globalmente o por tenant, según política).
- **Eventos que genera**: `UserRegistered`, `RoleAssigned`.

---

## 5. Contexto: Payments & Billing

### Aggregate Root: Payment
- **Propósito**: Registro de una transacción financiera.
- **Responsabilidad**: Interactuar con pasarelas externas y confirmar la recepción de fondos.
- **Invariantes**:
    - Un pago exitoso debe estar vinculado a una orden válida.
- **Eventos que genera**: `PaymentApproved`, `PaymentDeclined`.

## Referencias Cruzadas
- Ver [12. Entities](file:///D:/personales/development/ecommerce-platform/docs/domain/12-entities.md) para el detalle de los componentes internos de estos agregados.
