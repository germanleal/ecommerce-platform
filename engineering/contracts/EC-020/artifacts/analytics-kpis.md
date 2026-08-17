# Analytics KPI Model

| KPI | Fórmula / definición | Dimensiones |
|---|---|---|
| Ventas por día | suma de órdenes confirmadas/cobradas del día | tenant, tienda, moneda |
| Ticket promedio | ventas netas / órdenes cobradas | tenant, tienda, período |
| Productos más vendidos | unidades vendidas por producto | tenant, producto, período |
| Ingresos | cobros capturados menos reembolsos | tenant, moneda, período |
| Reembolsos | cantidad y monto procesado | tenant, motivo, período |
| Stock disponible | snapshot de disponible por inventario | tenant, warehouse, producto |
| Tiempo promedio de fulfillment | `readyForShippingAt - createdAt` | tenant, warehouse, período |

Cada KPI debe declarar zona horaria, moneda, ventana temporal, política de eventos anulados y precisión de agregación. Nunca se calcula con una consulta directa a tablas de otro dominio.
