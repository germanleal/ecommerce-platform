# 21. Estrategia de Versionado

## Versionado de Software
Se utiliza **Semantic Versioning 2.0.0 (SemVer)**:
`MAJOR.MINOR.PATCH`
- `MAJOR`: Cambios incompatibles en la API.
- `MINOR`: Nueva funcionalidad compatible hacia atrás.
- `PATCH`: Corrección de errores compatible hacia atrás.

## Versionado de APIs
- Incluir versión en el path: `/api/v1/...`.
- Mantener compatibilidad con al menos la versión anterior (N-1).

## Versionado de Base de Datos
- Gestionado por Flyway.
- Archivos de migración nombrados: `V[AÑO][MES][DIA][HORA][MIN]__descripcion.sql`.
- Las migraciones deben ser aditivas y compatibles hacia atrás siempre que sea posible.
