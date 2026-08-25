# Aggregate Root: InventoryItem

## 1. Identificación
- **Nombre**: InventoryItem
- **Contexto**: Store Operations / Inventory
- **Objetivo**: Representar y controlar la disponibilidad física de un SKU específico.
- **Responsabilidad principal**: Gestionar niveles de stock, reservas transaccionales y alertas de disponibilidad.

## 2. Límites
- **Qué pertenece**: Cantidad disponible, cantidad reservada, umbral de stock bajo.
- **Qué NO pertenece**: Descripción del producto, precios.
- **Qué reglas protege**: No vender más de lo disponible (si se requiere).
- **Qué datos controla**: `sku`, `store_id`, `available_quantity`, `reserved_quantity`, `low_stock_threshold`.
- **Qué comportamiento encapsula**: Ajuste de stock, reserva para pedidos, liberación de reservas.

## 3. Invariantes
- **BR-INV-01**: La cantidad disponible para la venta es siempre `total_quantity - reserved_quantity`.
- **BR-INV-02**: No se permiten reservas que excedan la cantidad disponible actual (a menos que el producto permita sobreventa).
- **BR-INV-03**: El SKU debe ser único dentro de la tienda.

## 4. Ciclo de Vida
### Estados
- **IN_STOCK**: Disponibilidad por encima del umbral.
- **LOW_STOCK**: Disponibilidad por debajo del umbral de alerta.
- **OUT_OF_STOCK**: Disponibilidad cero.

## 5. Responsabilidades
- **Qué hace**: Mantiene la exactitud de las unidades físicas disponibles.
- **Qué NO hace**: No conoce el precio ni las imágenes del producto.

## 6. Relaciones
- Se vincula mediante el `SKU` con la `ProductVariant` del contexto de Catálogo.
