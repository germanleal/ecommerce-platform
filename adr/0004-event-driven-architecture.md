# ADR 0004: Event Driven Architecture (EDA)

## Estado
Aceptado

## Contexto
En una arquitectura de microservicios, la comunicación puramente síncrona (REST/gRPC) puede llevar a un acoplamiento temporal y fallos en cascada.

## Problema
¿Cómo comunicar los microservicios de forma eficiente y resiliente?

## Alternativas Consideradas
- **Comunicación Síncrona (REST)**: Simple pero acoplada.
- **Coreografía de Eventos**: Desacoplada y escalable.

## Decisión
Utilizar una arquitectura orientada a eventos (EDA). Los microservicios emitirán "Eventos de Dominio" para notificar cambios de estado, y otros servicios reaccionarán de forma asíncrona.

## Consecuencias
- **Positivas**: Máximo desacoplamiento, mayor resiliencia, soporte nativo para consistencia eventual.
- **Negativas**: Mayor complejidad en el seguimiento de flujos de negocio y gestión de la consistencia eventual.

## Impacto Futuro
Facilita la integración de nuevas funcionalidades (ej. analítica, notificaciones) simplemente escuchando eventos existentes.
