# Tactical DDD Risks Identification

## 1. Riesgos de Diseño
### RD-001: Agregado Order de Alta Complejidad
- **Descripción**: El agregado `Order` maneja múltiples estados, line items, direcciones y totales.
- **Impacto**: Alta. Dificultad en la implementación y mantenimiento.
- **Probabilidad**: Media.
- **Severidad**: Crítica.
- **Mitigación**: Uso obligatorio de `OrderFactory` y `Domain Policies` para desacoplar transiciones de estado complejas.

### RD-002: Acoplamiento por SKU
- **Descripción**: La dependencia entre `ProductVariant` e `InventoryItem` a través del `SKU`.
- **Impacto**: Media. Cambios en la estructura de SKU afectan ambos contextos.
- **Probabilidad**: Baja.
- **Severidad**: Media.
- **Mitigación**: Establecer el SKU como un **Value Object** inmutable compartido conceptualmente (Published Language).

## 2. Riesgos de Implementación Futura
### RI-001: Consistencia Eventual en Inventario
- **Descripción**: La reserva de stock es asíncrona respecto a la creación del pedido.
- **Impacto**: Alta. Posibilidad de sobreventa si los eventos se retrasan.
- **Probabilidad**: Media.
- **Severidad**: Alta.
- **Mitigación**: Implementar el patrón `Saga` o `Transactional Outbox` en el BC de Order para asegurar la comunicación con Inventory.

### RI-002: Aislamiento Multi-Tenant en Persistencia
- **Descripción**: Riesgo de que una consulta mal formada exponga datos de otro tenant.
- **Impacto**: Crítica. Brecha de seguridad y confianza.
- **Probabilidad**: Baja (con frameworks adecuados).
- **Severidad**: Crítica.
- **Mitigación**: Implementar filtros de persistencia globales a nivel de repositorio que inyecten el `tenant_id` automáticamente.

## 3. Riesgos para Desarrollo con IA
### RA-001: Reinterpretación del Dominio
- **Descripción**: Que un asistente IA cree entidades o lógica fuera de lo definido en EC-003.
- **Impacto**: Media. Inconsistencia técnica.
- **Probabilidad**: Alta.
- **Severidad**: Media.
- **Mitigación**: Uso del `AI_CONTEXT.md` y validaciones automáticas de trazabilidad en cada iteración.
