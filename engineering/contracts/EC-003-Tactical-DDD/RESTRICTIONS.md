# EC-003: Tactical DDD - Restrictions

- **Agnosticismo Tecnológico**: Prohibido el uso de tipos de datos o conceptos de frameworks (ej. `Long` vs `Integer` en Java, anotaciones `@Entity`).
- **Encapsulamiento Estricto**: El estado interno de un Agregado solo puede ser modificado a través de sus métodos públicos.
- **Aislamiento Multi-Tenant**: Todo Agregado que pertenezca a un Tenant debe incluir obligatoriamente el `tenant_id` en su identidad o estado raíz.
- **Referencias Débiles**: Los agregados de distintos contextos solo pueden referenciarse mediante IDs, nunca mediante objetos completos.
