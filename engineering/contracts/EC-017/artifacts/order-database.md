# Order Database

Flyway `V1__create_orders.sql` creates `orders` and `order_items`. It includes tenant-scoped unique order numbers, foreign key from items to orders, positive/non-negative monetary constraints and tenant/customer, tenant/store and tenant/order indexes.

Repositories expose only tenant-scoped methods; no unfiltered `findAll()` operation exists. JDBC mappers rehydrate the aggregate and its items.

