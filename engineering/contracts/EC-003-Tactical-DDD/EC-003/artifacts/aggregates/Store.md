# Aggregate Root: Store

## 1. Identificación
- **Nombre**: Store
- **Contexto**: Store Operations
- **Objetivo**: Representar una unidad de venta independiente con identidad propia.
- **Responsabilidad principal**: Gestionar la configuración visual, operativa y la identidad de la tienda dentro del marketplace.

## 2. Límites
- **Qué pertenece**: Nombre de la tienda, configuración de marca (branding), dominio/subdominio, categorías del catálogo.
- **Qué NO pertenece**: Productos individuales (Aggregate propio), inventario, pedidos.
- **Qué reglas protege**: Unicidad de dominio, pertenencia obligatoria a un Tenant.
- **Qué datos controla**: `store_id`, `tenant_id`, `display_name`, `subdomain`, `branding_config`.
- **Qué comportamiento encapsula**: Personalización de la interfaz de la tienda, gestión de taxonomía (categorías).

## 3. Invariantes
- **BR-ST-01**: Una tienda debe estar vinculada a un Tenant activo.
- **BR-ST-02**: El subdominio de la tienda debe ser único en toda la plataforma.
- **BR-ST-03**: Toda categoría creada debe tener un nombre no vacío y pertenecer a la tienda.

## 4. Ciclo de Vida
### Estados
- **DRAFT**: Tienda en proceso de configuración inicial, no visible en el marketplace.
- **PUBLISHED**: Tienda activa y visible para clientes.
- **MAINTENANCE**: Visible pero con transacciones deshabilitadas.
- **CLOSED**: No visible, operación cesada.

### Transiciones
- `DRAFT` -> `PUBLISHED`: Realizado por `Store Manager` tras completar configuración mínima.
- `PUBLISHED` -> `MAINTENANCE`: Manual para cambios estructurales.
- `*` -> `CLOSED`: Por decisión del `Tenant Administrator`.

## 5. Responsabilidades
- **Qué hace**: Gestiona la estructura del catálogo (categorías) y la apariencia visual.
- **Qué NO hace**: No gestiona el stock de productos ni los precios (responsabilidad de `Product` e `InventoryItem`).

## 6. Relaciones
- Es el padre de las `Category` (Entity) que organizan el catálogo.
- Mantiene una relación de 1:N con los `Product` de la tienda.
