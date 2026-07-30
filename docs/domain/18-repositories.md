# 18. Repositories (Conceptuales)

## Objetivo
Definir las interfaces de persistencia y recuperación de Agregados, sin entrar en detalles de implementación tecnológica.

## Catálogo de Repositorios

| Repositorio | Aggregate Administrado | Operaciones Clave |
| :--- | :--- | :--- |
| **TenantRepository** | Tenant | `save(Tenant)`, `findById(TenantId)`, `findByDomain(String)` |
| **ProductRepository** | Product | `save(Product)`, `findByStore(StoreId)`, `findPublished(StoreId)` |
| **OrderRepository** | Order | `save(Order)`, `findById(OrderId)`, `findByCustomer(CustomerId)` |
| **InventoryRepository** | InventoryItem | `findBySku(SKU)`, `updateStock(SKU, Quantity)` |

## Restricciones Generales
- Los repositorios solo deben devolver y persistir **Aggregates completos**.
- No se permiten consultas que crucen límites de Bounded Context directamente a nivel de base de datos.
