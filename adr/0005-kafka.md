# ADR 0005: Apache Kafka como Broker de Mensajería

## Estado
Aceptado

## Contexto
Necesitamos una infraestructura de mensajería que soporte alta disponibilidad, persistencia de eventos y gran escalabilidad.

## Problema
¿Qué herramienta de mensajería utilizar para implementar EDA?

## Alternativas Consideradas
- **RabbitMQ**: Excelente para mensajería tradicional, pero menos orientado a streaming y persistencia a largo plazo.
- **Apache Kafka**: Estándar de la industria para arquitecturas de eventos y streaming de datos.

## Decisión
Utilizar Apache Kafka. Kafka proporciona las garantías de durabilidad, ordenamiento y escalabilidad necesarias para un sistema SaaS enterprise.

## Consecuencias
- **Positivas**: Persistencia de eventos (Event Store conceptual), alta throughput, ecosistema maduro (Kafka Connect, KSQL).
- **Negativas**: Mayor complejidad de administración y configuración en comparación con brokers más simples.

## Impacto Futuro
Permite el procesamiento de datos en tiempo real y la reconstrucción de estados de negocio a partir del log de eventos.
