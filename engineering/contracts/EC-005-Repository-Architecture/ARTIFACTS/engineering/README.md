# EC-005 Parte 2: Engineering Repository Structure

Este documento define la estructura oficial de `engineering/`.

## Estructura Raíz de Engineering

El directorio `engineering/` debe contener toda la documentación de ingeniería y gobernanza.

Estructura mínima requerida:

```
engineering/
├── contracts/
├── templates/
├── standards/
├── guidelines/
├── reviews/
├── metrics/
├── quality/
├── adr/
├── ai/
└── governance/
```

### Principios

- Cada contrato debe vivir en su propio subdirectorio.
- No almacenar documentación de usuario o funcional dentro de `engineering/`.
- `adr/` contiene las Architecture Decision Records globales.
- `ai/` contiene prompts y contexto para asistentes IA.
- `governance/` contiene reglas, métricas y políticas.

## Contenido típico

- `contracts/`: engineering contracts como EC-004, EC-005, EC-006.
- `templates/`: plantillas para documentos, ADRs y servicios.
- `standards/`: estándares de desarrollo.
- `guidelines/`: guías operativas y de calidad.
- `reviews/`: resultados de arquitecturas y revisiones.
- `metrics/`: métricas de calidad técnica.
- `quality/`: políticas de calidad.
