# ADR 0002: Adopción de Arquitectura Hexagonal

## Estado
Aceptado

## Contexto
Necesitamos que la lógica de negocio sea independiente de los detalles técnicos (bases de datos, frameworks, APIs externas) para garantizar la testabilidad y la evolución tecnológica.

## Problema
¿Cómo estructurar los componentes del sistema para minimizar el acoplamiento con la infraestructura?

## Alternativas Consideradas
- **Arquitectura en Capas Tradicional**: Acoplamiento fuerte entre capas.
- **Clean Architecture**: Muy similar a Hexagonal, pero con capas más estrictas.

## Decisión
Implementar Arquitectura Hexagonal (Ports & Adapters). El núcleo del sistema será el Dominio, rodeado por la capa de Aplicación (Use Cases), y finalmente la capa de Infraestructura (Adaptadores).

## Consecuencias
- **Positivas**: Testabilidad total de la lógica de negocio sin mocks complejos de frameworks, facilidad para cambiar componentes de infraestructura (ej. cambiar de base de datos o API externa).
- **Negativas**: Incremento en el número de clases e interfaces.

## Impacto Futuro
Permite mantener la plataforma vigente tecnológicamente durante años al facilitar actualizaciones de frameworks y herramientas sin tocar el núcleo del negocio.
