# Dead Letter Queue

`integration_retry_queue` soporta la marca `dead_letter` y conserva tenant, job, payload, error, fecha y número de intentos. La política operativa de promoción a DLQ y replay requiere validación de infraestructura Kafka en la siguiente fase.
