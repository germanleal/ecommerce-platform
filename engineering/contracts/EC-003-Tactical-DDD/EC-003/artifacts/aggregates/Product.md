# Aggregate Root: Product

## 1. Identificación
- **Nombre**: Product
- **Contexto**: Store Operations
- **Objetivo**: Representar la oferta comercial de un artículo en una tienda.
- **Responsabilidad principal**: Gestionar la información descriptiva, variantes y precios del producto.

## 2. Límites
- **Qué pertenece**: Descripción del producto, imágenes, variantes, precios.
- **Qué NO pertenece**: Niveles de stock (InventoryItem), comentarios de clientes.
- **Qué reglas protege**: Integridad de variantes, validez de precios.
- **Qué datos controla**: `product_id`, `store_id`, `tenant_id`, `name`, `description`, `variants`, `base_price`.
- **Qué comportamiento encapsula**: Publicación, actualización de precios, gestión de atributos de variantes.

## 3. Invariantes
- **BR-PR-01**: Un producto debe tener al menos una `ProductVariant` activa para poder ser publicado.
- **BR-PR-02**: El precio base del producto no puede ser negativo.
- **BR-PR-03**: Todas las variantes de un producto deben compartir la misma moneda definida por la tienda.

## 4. Ciclo de Vida
### Estados
- **INACTIVE**: Creado pero no disponible para la venta.
- **ACTIVE**: Disponible y visible si la tienda está publicada.
- **DISCONTINUED**: No se venderá más, pero se mantiene por historial de pedidos.

### Transiciones
- `INACTIVE` -> `ACTIVE`: Tras validar requisitos de publicación.
- `ACTIVE` -> `INACTIVE`: Retirada temporal.
- `*` -> `DISCONTINUED`: Baja definitiva del catálogo.

## 5. Responsabilidades
- **Qué hace**: Define cómo se ve el producto y cuánto cuesta.
- **Qué NO hace**: No sabe cuántas unidades físicas hay disponibles.

## 6. Relaciones
- Contiene una colección de `ProductVariant` (Entity).
- Referencia a la `Category` a la que pertenece en la `Store`.
