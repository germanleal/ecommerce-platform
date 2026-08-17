# EC-021 Parte 2 — Integration API

`integration-service` expone DTOs REST para conectores y jobs bajo `/integration`. Las consultas usan tenant context; no existe endpoint para seleccionar otro tenant.

- `/connectors` registra, actualiza, lista, habilita y deshabilita conectores.
- `/jobs` ejecuta, lista, consulta, reintenta y cancela jobs.
