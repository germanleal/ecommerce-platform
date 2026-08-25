# 12. Entities

## Objetivo
Definir las entidades dentro de cada Agregado que poseen identidad propia y un ciclo de vida, pero que no son la raíz del mismo.

## 1. Contexto: Store Operations

### Entity: ProductVariant
- **Aggregate Root**: `Product`
- **Responsabilidad**: Representar una combinación específica de atributos de un producto (ej. Talla L, Color Rojo).
- **Identidad**: SKU (Stock Keeping Unit).
- **Reglas**: Cada variante puede tener su propio precio y peso.

### Entity: Category
- **Aggregate Root**: `Store` (o Agregado propio de Taxonomía si crece mucho).
- **Responsabilidad**: Organizar los productos en estructuras jerárquicas.
- **Identidad**: UUID de categoría.
- **Reglas**: Puede tener una categoría padre (jerarquía).

---

## 2. Contexto: Order Fulfillment

### Entity: LineItem
- **Aggregate Root**: `Order`
- **Responsabilidad**: Representar un producto específico dentro de un pedido en un momento dado.
- **Identidad**: ID interno de línea de pedido.
- **Reglas**: Debe capturar el precio y el nombre del producto al momento de la compra (inmutabilidad histórica).

---

## 3. Contexto: Provisioning

### Entity: StoreInstance
- **Aggregate Root**: `Tenant`
- **Responsabilidad**: Registro administrativo de una tienda dentro de la organización.
- **Identidad**: ID de Tienda.
- **Reglas**: No puede existir sin un Tenant asociado.

## Resumen de Entidades

| Entidad | Agregado Raíz | Identificador |
| :--- | :--- | :--- |
| ProductVariant | Product | SKU |
| Category | Store | CategoryId |
| LineItem | Order | LineItemId |
| StoreInstance | Tenant | StoreId |

## Referencias Cruzadas
- Ver [13. Value Objects](file:///D:/personales/development/ecommerce-platform/docs/domain/13-value-objects.md) para los objetos sin identidad que complementan estas entidades.
