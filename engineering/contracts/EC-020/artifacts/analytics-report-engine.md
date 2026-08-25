# Report Engine

`ReportEngineService` expone reportes de ventas, órdenes, pagos, refunds, inventario y fulfillment. Los reportes aceptan rango temporal cuando el read model contiene `occurred_at`; siempre filtran por el tenant derivado del JWT.
