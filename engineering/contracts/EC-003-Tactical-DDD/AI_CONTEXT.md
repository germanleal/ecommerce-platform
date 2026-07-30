# AI Context: Tactical DDD Model (EC-003)

## 1. Misión del Agente
Todo asistente IA que trabaje en fases posteriores debe considerar el modelo táctico de EC-003 como la **Especificación Técnica Definitiva** del negocio. El código debe adaptarse al dominio, nunca al revés.

## 2. Reglas de Oro para la IA
- **Respetar Límites**: No cruzar fronteras de agregados ni contextos sin usar los mecanismos definidos (Eventos o Domain Services).
- **Proteger Invariantes**: Cada línea de código de negocio debe validar las invariantes documentadas en `artifacts/aggregates/` e `artifacts/invariants/`.
- **Lenguaje Ubicuo**: Usar estrictamente los nombres de clases y métodos derivados del lenguaje ubicuo.
- **Aislamiento Multi-Tenant**: Cualquier lógica de recuperación de datos debe incluir el filtro por `tenant_id` derivado del contexto de ejecución.

## 3. Prohibiciones
- **No crear Agregados nuevos** sin un ADR aprobado.
- **No modificar estados** de forma arbitraria (usar métodos expresivos en el dominio).
- **No introducir dependencias técnicas** (ej. JPA, Kafka) dentro de las clases de dominio.

## 4. Flujo de Consulta
Antes de implementar cualquier funcionalidad:
1. Leer `artifacts/use-cases/` para entender el flujo.
2. Leer `artifacts/aggregates/` para entender las reglas del objeto principal.
3. Consultar `artifacts/traceability/TraceabilityMatrix.md` para verificar impactos.
