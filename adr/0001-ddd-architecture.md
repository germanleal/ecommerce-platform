# ADR 0001: Adopción de Domain Driven Design (DDD)

## Estado
Aceptado

## Contexto
La plataforma es un sistema SaaS complejo con múltiples reglas de negocio y entidades interrelacionadas (Tenants, Tiendas, Productos, Pedidos). Un enfoque tradicional centrado en datos podría llevar a un modelo anémico y difícil de mantener a medida que el sistema crece.

## Problema
¿Cómo organizar la lógica de negocio para que sea escalable, mantenible y alineada con los objetivos del negocio?

## Alternativas Consideradas
- **CRUD Tradicional**: Fácil de implementar inicialmente pero difícil de mantener en sistemas complejos.
- **DDD Lite**: Uso de tácticas DDD sin el modelado estratégico completo.

## Decisión
Adoptar Domain Driven Design (DDD) completo, incluyendo tanto el diseño estratégico (Bounded Contexts, Ubiquitous Language, Context Mapping) como el diseño táctico (Aggregates, Entities, Value Objects, Domain Events).

## Consecuencias
- **Positivas**: Mayor alineación con el negocio, lenguaje común entre técnicos y expertos, código más robusto y testeable.
- **Negativas**: Mayor curva de aprendizaje inicial y más verbosidad en el código.

## Impacto Futuro
Facilita la evolución del sistema y la integración de nuevos desarrolladores y agentes de IA al tener límites claros y reglas de negocio explícitas.
