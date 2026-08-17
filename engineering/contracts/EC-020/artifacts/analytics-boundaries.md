# Analytics Context Boundaries

## Contextos

- **Analytics Context:** ingestión, normalización y proyección de hechos analíticos.
- **Reporting Context:** consultas, filtros, agregaciones y exportaciones de reportes.
- **KPI Context:** definición, cálculo, objetivo y tendencia de indicadores.

## Ownership

Marketplace posee stores y catálogo; Commerce posee precios y carrito; Order posee órdenes; Payments posee cobros y reembolsos; Inventory posee stock y fulfillment. Analytics solo posee sus read models derivados.

La integración es exclusivamente Kafka/event-driven. No existen FK, llamadas sincrónicas ni escrituras hacia los dominios fuente.

## Exclusiones

No incluye transacciones, corrección de datos fuente, logística de despacho, tracking, Power BI, Data Lake, ML o IA.
