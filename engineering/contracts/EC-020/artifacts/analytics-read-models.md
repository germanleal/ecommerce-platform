# Analytics Read Models

| Read model | Fuentes | Actualización | Consistencia |
|---|---|---|---|
| SalesDaily | Order, payment, refund | Por evento y cierre diario | Eventual |
| SalesByStore | Order, Commerce, Marketplace | Por evento | Eventual |
| ProductSales | Order, Commerce | Por evento | Eventual |
| PaymentSummary | Payments, refunds | Por evento | Eventual |
| InventorySnapshot | Inventory events | Por evento y snapshot | Eventual |
| FulfillmentPerformance | Fulfillment, picking, packing | Por evento | Eventual |

Todos los modelos incluyen `tenantId`, claves de negocio, período, `sourceEventId`, `schemaVersion`, `occurredAt`, `projectedAt` y una versión de proyección.

La ingestión debe ser idempotente por `sourceEventId`; eventos fuera de orden se corrigen mediante replay o recomputación de la proyección.
