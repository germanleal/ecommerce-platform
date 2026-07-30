# 22. Flujo de Desarrollo

## Enfoque por Fases
El proyecto sigue un flujo de construcción incremental basado en fases especializadas para garantizar la calidad y consistencia:
1. **Strategic Domain**: Definición de límites y capacidades (Fase 1).
2. **Tactical Domain**: Modelado interno de agregados y reglas (Fase 2).
3. **Architecture & Contracts**: Definición de interfaces e integración (Fases 3-4).
4. **Implementation**: Desarrollo de microservicios y frontend (Fases 5+).

## Pasos para una nueva funcionalidad (Fase de Implementación)
1. **Análisis**: Entender el requerimiento y su impacto en el dominio.
2. **Diseño**: Definir contratos de API y eventos. Crear/Actualizar ADR si es necesario.
3. **Desarrollo (TDD preferido)**:
   - Crear tests de dominio.
   - Implementar lógica de dominio.
   - Implementar adaptadores de infraestructura.
4. **Verificación**: Ejecutar tests locales y validación con Docker Compose.
5. **Revisión**: Pull Request y revisión por pares (Humanos o IA).
6. **Merge**: Integración en `main` tras aprobación.

## Definition of Done (DoD)
Consultar el documento central de [DoD por Fase](file:///D:/personales/development/ecommerce-platform/docs/architecture/definition-of-done-per-phase.md) para los criterios específicos de cada etapa.
