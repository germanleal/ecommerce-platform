# Repository Validation Checklist

Este documento contiene las validaciones obligatorias que deben pasar para considerar la estructura correcta.

## Validaciones Automáticas

### Estructura
- [ ] No existen carpetas huérfanas.
- [ ] No existen nombres ambiguos.
- [ ] No existen proyectos fuera de la estructura.
- [ ] No existen carpetas duplicadas.

### Backend
- [ ] Todos los microservicios poseen la estructura estándar.
- [ ] Todos contienen `README`.
- [ ] Todos contienen `CHANGELOG`.
- [ ] Todos contienen carpeta `docs`.
- [ ] Todos contienen `ADR`.

### Frontend
- [ ] Todos los proyectos poseen la estructura estándar.
- [ ] Existe Design System independiente.
- [ ] Existe Shared UI.
- [ ] Existe documentación.

### Shared
- [ ] No existe lógica de negocio compartida.
- [ ] No existen dependencias circulares.
- [ ] Las librerías están documentadas.

### Infrastructure
- [ ] Todos los componentes viven dentro de `infrastructure/`.
- [ ] No existe infraestructura distribuida en otros proyectos.

### Engineering
- [ ] Todos los Engineering Contracts están versionados.
- [ ] Existe trazabilidad entre contratos.
- [ ] Existe documentación completa.
