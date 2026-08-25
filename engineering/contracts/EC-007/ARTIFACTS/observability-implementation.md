# EC-007 Parte 3 — Observability implementation

Actuator expone `health`, `info` y `metrics` en los servicios containerizados. `LOG_LEVEL` se configura externamente por perfil. Correlation/tenant context ya existe en varios servicios por filtros/proveedores, pero la uniformidad de logs JSON y exportación de métricas/traces requiere la fase operacional siguiente.
