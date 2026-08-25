# 20. Traceability Matrix

## Objetivo
Garantizar que cada elemento del diseño táctico responda a una necesidad estratégica definida anteriormente.

| Capability | Proceso | Caso de Uso | Aggregate | Evento | Regla |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Multi-Tenant Orchestration | Onboarding | UC-PROV-01 | Tenant | TenantRegistered | BR-MT-01 |
| Dynamic Catalog Engine | Gestión Catálogo | UC-OPS-01 | Product | ProductPublished | BR-CAT-02 |
| Order Management | Venta (Checkout) | UC-FULL-01 | Order | OrderPlaced | BR-ORD-01 |
| Inventory Control | Reserva Stock | UC-FULL-01 | InventoryItem | StockReserved | BR-CAT-01 |
| Identity & Access | Login | UC-IAM-01 | User | UserAuthenticated | BR-IAM-01 |

## Referencias Cruzadas
- Ver [02. Business Capabilities](file:///D:/personales/development/ecommerce-platform/docs/domain/02-business-capabilities.md) para el origen de las capacidades.
