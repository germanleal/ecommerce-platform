# Retry Engine

Los retries manuales generan una entrada persistente con `available_at`, `attempts`, payload, error y job. El cálculo usa backoff exponencial acotado. Kafka aplica tres reintentos con backoff fijo mediante `DefaultErrorHandler`.
