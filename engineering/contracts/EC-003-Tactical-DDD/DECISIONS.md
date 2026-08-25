# EC-003: Tactical DDD - Decisions

- **D-001: Agregado Order como Root**: Se decide que `Order` sea el agregador de `LineItem` para garantizar la consistencia de precios capturados y estados de envío.
- **D-002: SKU como Identidad de Variante**: Se utiliza el SKU como identificador natural para facilitar la integración con sistemas externos y el contexto de inventario.
- **D-003: Uso de Value Objects para Dinero**: Obligatorio el uso de un VO `Money` para evitar errores de coma flotante y asegurar la consistencia de divisas.
- **D-004: Eventos en Pasado**: Todos los eventos de dominio deben reflejar hechos ya ocurridos en el negocio (ej. `OrderPlaced`).
