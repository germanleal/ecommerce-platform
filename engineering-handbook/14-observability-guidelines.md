# 14. Guías de Observabilidad

## Logs
- Formato JSON para facilitar la ingesta y análisis.
- Inclusión de `correlation_id` para trazabilidad distribuida.
- Niveles de log adecuados (INFO para eventos de negocio, ERROR para fallos, DEBUG para desarrollo).

## Métricas
- Exposición de métricas mediante Micrometer y Prometheus.
- Métricas clave: Latencia, Tasa de Errores, Rendimiento (Throughput), Uso de recursos.

## Trazado Distribuido (Tracing)
- Implementación de OpenTelemetry o Micrometer Tracing.
- Visualización de trazas para identificar cuellos de botella en la comunicación entre microservicios.

## Health Checks
- Implementación de endpoints `/health` (Liveness y Readiness) en todos los servicios siguiendo el estándar de Spring Boot Actuator.
