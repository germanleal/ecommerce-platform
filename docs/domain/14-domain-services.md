# 14. Domain Services

## Objetivo
Identificar operaciones de negocio que no pertenecen naturalmente a un solo Agregado y que involucran múltiples entidades o lógica compleja de coordinación.

## Catálogo de Domain Services

### 1. CheckoutService
- **Responsabilidad**: Coordinar la creación de una `Order` validando simultáneamente el `Inventory`, aplicando `Promotions` y verificando el estado del `Tenant`.
- **¿Por qué Domain Service?**: Involucra múltiples Agregados (Order, Inventory, Tenant) que deben mantenerse consistentes durante la transacción.
- **Operaciones**: `processCheckout(Cart, Customer)`.

### 2. PricingEngine
- **Responsabilidad**: Calcular el precio final de un producto considerando reglas de impuestos regionales, descuentos vigentes y el plan del tenant.
- **¿Por qué Domain Service?**: La lógica de cálculo de precios puede depender de factores externos al Agregado `Product` (ej. reglas fiscales globales).
- **Operaciones**: `calculateFinalPrice(Product, Context)`.

### 3. InventoryReservationService
- **Responsabilidad**: Gestionar la reserva temporal de stock durante el flujo de compra y su liberación o confirmación final.
- **¿Por qué Domain Service?**: Debe interactuar con el contexto de órdenes y el de inventario de forma atómica.
- **Operaciones**: `reserveStock(SKU, Quantity)`, `releaseReservation(ReservationId)`.

## Resumen de Servicios

| Servicio | Contexto | Dependencias Conceptuales |
| :--- | :--- | :--- |
| CheckoutService | Fulfillment | Order, Inventory, Payments |
| PricingEngine | Catalog | Product, TaxRules, Promotions |
| TenantProvisioningService | Provisioning | Tenant, IAM, MailService |

## Referencias Cruzadas
- Ver [15. Domain Policies](file:///D:/personales/development/ecommerce-platform/docs/domain/15-domain-policies.md) para las reglas que estos servicios deben aplicar.
