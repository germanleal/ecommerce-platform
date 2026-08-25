# Definition of Done (DoD) por Fase

## Fase 1: DDD Strategic Design
- [ ] Domain Discovery completo y documentado.
- [ ] Business Capability Map sin duplicidades.
- [ ] Subdominios clasificados (Core, Supporting, Generic).
- [ ] Bounded Contexts con límites claros.
- [ ] Context Map completo y relaciones justificadas.
- [ ] Lenguaje Ubicuo definido por contexto.

## Fase 2: DDD Tactical Design
- [ ] Aggregate Roots con invariantes documentadas.
- [ ] Entities y Value Objects correctamente clasificados.
- [ ] Domain Services y Policies justificados.
- [ ] Casos de Uso trazados hasta Capabilities.
- [ ] Matriz de Trazabilidad completa.
- [ ] Modelo totalmente desacoplado de tecnología.

## Fase 3: Architecture Integration
- [ ] Especificaciones OpenAPI para todas las APIs de negocio.
- [ ] Especificaciones AsyncAPI para todos los eventos Kafka.
- [ ] Definición de estrategias de compensación para consistencia eventual.

## Fase 4: Repository Architecture
- [ ] Estrategia de aislamiento de datos Multi-Tenant validada.
- [ ] Esquemas de base de datos alineados con los Agregados.

## Fase 5: Engineering Templates
- [ ] Arquetipos Maven/React funcionales y probados.
- [ ] Documentación de uso de plantillas completada.

## Fase 6: Platform Bootstrap
- [ ] Repositorios creados siguiendo la estructura estándar.
- [ ] Pipelines de CI iniciales operativos.
