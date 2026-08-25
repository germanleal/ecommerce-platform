# Analytics Ubiquitous Language

| Concepto | Definición | Regla |
|---|---|---|
| Metric | Medición cuantitativa observada | Debe declarar unidad y período |
| KPI | Métrica vinculada a un objetivo | Debe declarar fórmula y dimensiones |
| Dashboard | Vista compuesta de KPIs y tendencias | Solo lectura y tenant-scoped |
| Report | Resultado consultable de un read model | No modifica fuentes |
| Read Model | Proyección optimizada para consulta | Se reconstruye desde eventos |
| Projection | Proceso que transforma eventos en read model | Idempotente por eventId |
| Aggregation | Cálculo agrupado por dimensiones | Declara ventana y zona horaria |
| Trend | Evolución de una métrica en el tiempo | Requiere períodos comparables |
| Snapshot | Estado calculado en un instante | Incluye occurredAt y projectedAt |
| Business Indicator | Indicador interpretado para una decisión | Debe tener definición operativa |

## Clasificación de subdominios

Analytics/KPI es core para la capacidad de decisión de la plataforma; reporting es supporting; autenticación, exportación técnica y transporte Kafka son capacidades genéricas.

## Actores

Platform Administrator gobierna la plataforma; Tenant Administrator administra acceso de su tenant; Business Analyst explora reportes; Operations Manager consulta operación; Sales Manager consulta ventas; Finance Manager consulta cobros; Warehouse Manager consulta stock y fulfillment; Executive consulta KPIs agregados autorizados.
