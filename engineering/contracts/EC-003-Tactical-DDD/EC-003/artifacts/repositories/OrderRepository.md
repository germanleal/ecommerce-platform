# Repository: OrderRepository

## Aggregate Administrado
**Order**.

## Responsabilidad
Proveer persistencia y recuperación de los pedidos, garantizando que el agregado se cargue siempre en un estado consistente y completo (incluyendo sus line items).

## Operaciones Conceptuales
- `save(order)`: Persiste o actualiza el estado de la orden.
- `findById(orderId)`: Recupera una orden específica.
- `findByCustomer(customerId)`: Recupera el historial de pedidos de un cliente.
- `findByStore(storeId, status)`: Recupera pedidos para gestión de la tienda.

## Restricciones
- Las consultas deben estar siempre filtradas implícita o explícitamente por `tenant_id` para garantizar el aislamiento Multi-Tenant.
