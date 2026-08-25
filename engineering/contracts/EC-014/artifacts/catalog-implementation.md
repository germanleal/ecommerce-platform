# Catalog Implementation

Implementados los modelos Store, Catalog, Category y Product con invariantes de tenant, ownership y lifecycle. La persistencia usa Flyway y los repositorios deben resolver siempre por `tenantId`.
