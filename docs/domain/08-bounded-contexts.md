# 08. Bounded Contexts

## Objetivo
Definir los límites lógicos del sistema de software, donde cada contexto tiene su propio modelo de dominio, lenguaje ubicuo y responsabilidades claras.

## Catálogo de Bounded Contexts (BC)

### 1. BC: Provisioning & Tenant Management
- **Propósito**: Administrar la creación y configuración de las organizaciones (Tenants).
- **Responsabilidad**: Ciclo de vida del tenant, gestión de suscripciones, límites de recursos.
- **Lenguaje Clave**: Tenant, Plan, Subscription, Provisioning.
- **Actores**: Platform Admin, Tenant Admin.
- **Límites**: No sabe nada de productos ni de pedidos. Solo conoce organizaciones.

### 2. BC: Marketplace Discovery
- **Propósito**: Proporcionar una interfaz unificada para buscar y encontrar tiendas y productos.
- **Responsabilidad**: Indexación de productos públicos, búsqueda semántica, recomendaciones.
- **Lenguaje Clave**: Listing, Search Result, Recommendation, Global Category.
- **Actores**: Guest, Customer.
- **Límites**: Solo consume datos publicados de otros contextos. Es de solo lectura en gran parte.

### 3. BC: Store Operations (Catalog & Inventory)
- **Propósito**: Permitir la operación diaria de una tienda específica.
- **Responsabilidad**: Gestión de catálogo, variantes, precios y niveles de stock.
- **Lenguaje Clave**: Product, SKU, Category, Variant, Stock Level.
- **Actores**: Store Manager.
- **Límites**: Es el dueño de la verdad sobre lo que se vende y cuánto hay.

### 4. BC: Order Fulfillment
- **Propósito**: Orquestar el proceso de venta y entrega.
- **Responsabilidad**: Gestión de estados de pedido, cálculo de totales, coordinación con pagos.
- **Lenguaje Clave**: Order, Line Item, Shipping Address, Status.
- **Actores**: Customer, Store Manager.
- **Límites**: No gestiona el catálogo, solo hace referencia a IDs de productos y precios al momento de la venta.

### 5. BC: Identity & Access (IAM)
- **Propósito**: Centralizar la seguridad y perfiles.
- **Responsabilidad**: Autenticación, gestión de roles (RBAC), perfiles de usuario.
- **Lenguaje Clave**: User, Role, Permission, Token.
- **Actores**: Todos.
- **Límites**: Contexto genérico que sirve a todos los demás para validar identidad.

### 6. BC: Payments & Billing
- **Propósito**: Gestionar la integración financiera.
- **Responsabilidad**: Procesamiento de transacciones de clientes y facturación a tenants.
- **Lenguaje Clave**: Transaction, Invoice, Payment Method, Refund.
- **Actores**: Customer, Platform Admin (para cobro a tenants).

## Resumen de Contextos y Relaciones Primarias

| Bounded Context | Subdominio Relacionado | Dueño del Modelo |
| :--- | :--- | :--- |
| Provisioning | Multi-Tenant Management | Platform Team |
| Discovery | Marketplace Discovery | Growth Team |
| Operations | Cataloging / Inventory | Store Team |
| Fulfillment | Order Fulfillment | Checkout Team |
| IAM | Identity & Access | Platform/Security Team |

## Referencias Cruzadas
- Ver [09. Context Map](file:///D:/personales/development/ecommerce-platform/docs/domain/09-context-map.md) para ver cómo estos contextos se comunican entre sí.
- Ver [10. Ubiquitous Language](file:///D:/personales/development/ecommerce-platform/docs/domain/10-ubiquitous-language.md) para el detalle del vocabulario por contexto.
