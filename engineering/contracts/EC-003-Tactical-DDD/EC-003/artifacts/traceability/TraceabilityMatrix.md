# Tactical Traceability Matrix

## Mapeo de Capacidades a Artefactos Tácticos

| Business Capability | Business Process | Use Case | Aggregate Root | Domain Event |
| :--- | :--- | :--- | :--- | :--- |
| **Order Management** | Checkout & Purchase | `PlaceOrder` | `Order` | `OrderPlaced` |
| **Catalog Management** | Product Publishing | `PublishProduct` | `Product` | `ProductPublished` |
| **Inventory Control** | Stock Adjustment | `AdjustStock` | `InventoryItem` | `StockAdjusted` |
| **Tenant Onboarding** | Account Registration | `RegisterTenant` | `Tenant` | `TenantRegistered` |
| **Store Setup** | Store Creation | `CreateStore` | `Store` | `StoreCreated` |

## Mapeo Interno de Agregados

| Aggregate Root | Entities | Value Objects | Policies |
| :--- | :--- | :--- | :--- |
| **Order** | `LineItem` | `Address`, `Money` | `AutoCancelOrder...` |
| **Product** | `ProductVariant` | `Price`, `SKU` | - |
| **Tenant** | `StoreInstance` | - | `SuspendStore...` |
| **InventoryItem** | - | `SKU` | `LowStockNotify...` |

## Validación de Trazabilidad
- Todos los Agregados responden a al menos una Business Capability.
- Todos los Casos de Uso están vinculados a un Proceso de Negocio definido en EC-001/EC-002.
