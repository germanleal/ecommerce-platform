# Warehouse Model

`Warehouse` pertenece a un tenant y contiene `WarehouseLocation`. Un tenant puede tener multiples bodegas. La asignacion de stock selecciona una o mas ubicaciones usando reglas posteriores de disponibilidad y prioridad.

Warehouse administra identidad, estado activo, capacidad y ubicaciones. No administra precios, pedidos ni pagos.
