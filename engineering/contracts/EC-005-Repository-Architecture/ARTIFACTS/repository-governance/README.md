# Repository Governance

Este documento define el flujo de gobernanza para cambios estructurales en el repositorio.

## Flujo de modificación estructural

1. Solicitud
2. Análisis Arquitectónico
3. Evaluación de Impacto
4. Actualización del Engineering Contract
5. Aprobación
6. Implementación
7. Revisión
8. Actualización Documental

## Reglas para desarrolladores

### Prohibiciones
- No crear carpetas arbitrarias.
- No crear módulos duplicados.
- No almacenar documentación fuera de `docs/` o `engineering/`.
- No colocar scripts dentro del código fuente.
- No mezclar frontend con backend.
- No mezclar infraestructura con dominio.
- No mezclar dominio con adaptadores tecnológicos.

### Obligaciones
- Actualizar `README` cuando cambie la estructura.
- Actualizar `CHANGELOG`.
- Actualizar documentación.
- Actualizar diagramas cuando corresponda.
- Actualizar ADR cuando exista una decisión arquitectónica.

## Reglas para asistentes IA

### Antes de generar código
- Leer: Engineering Handbook, EC-002, EC-003, EC-004, EC-005.
- Validar que la estructura esperada exista.

### Durante la generación
- No crear carpetas nuevas sin justificación.
- No modificar la estructura aprobada.
- No mover archivos existentes.
- No cambiar convenciones de nombres.
- No crear proyectos fuera de la estructura oficial.
- No duplicar componentes.
- No crear código fuera del contexto del Engineering Contract activo.

### Después de generar código
- Actualizar automáticamente: `README`, `CHANGELOG`, documentación afectada.

### Cambios estructurales
Si un asistente considera necesario modificar la estructura deberá:
1. Documentar el problema.
2. Identificar el impacto.
3. Generar una propuesta de cambio.
4. Esperar aprobación arquitectónica antes de modificar el repositorio.
