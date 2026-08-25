# EC-003: Tactical DDD - Quality Metrics

## 1. Métricas de Dominio
- **Cobertura de Contextos**: 100% (Todos los Bounded Contexts modelados).
- **Densidad de Invariantes**: Al menos 2 invariantes críticas documentadas por Aggregate Root.
- **Trazabilidad**: 100% de los Casos de Uso vinculados a un Aggregate y una Capability.

## 2. Métricas de Arquitectura
- **Pureza del Modelo**: 0 dependencias a frameworks externos (Spring, Hibernate, etc.).
- **Desacoplamiento**: 0 referencias directas de ID entre agregados de distintos contextos (uso exclusivo de referencias débiles o eventos).
- **Justificación**: 100% de los Domain Services tienen una justificación documentada de por qué no pertenecen a un Agregado.

## 3. Métricas para IA
- **Contexto Disponible**: `AI_CONTEXT.md` creado y actualizado.
- **Estructura Estándar**: 100% de los artefactos siguen la estructura de carpetas de gobernanza.
