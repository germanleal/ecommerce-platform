# Kafka Validation Report

## Resultado

`docker compose config --quiet` valida la configuración sintáctica, pero no demuestra que Kafka esté operativo ni que los flujos consuman correctamente.

## Faltantes críticos

- No se ejecutó una prueba con broker Kafka real.
- No existe configuración uniforme de `DeadLetterPublishingRecoverer`/DLQ.
- No hay prueba automatizada de publicación, consumo, retry, duplicado y recuperación.
- Orders y Payments no poseen `@KafkaListener` para completar la saga.
