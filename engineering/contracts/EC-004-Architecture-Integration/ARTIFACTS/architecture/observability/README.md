# Observability Architecture

Este documento describe la estrategia de observabilidad para la plataforma.

## Objetivo

- Definir cómo se generan logs, métricas, health checks y trazas.
- Establecer qué información debe producir cada componente.
- Alinear la observabilidad con el modelo de microservicios.

## Principios

- Observability by Design.
- Cada servicio emite logs estructurados, métricas y health checks.
- Correlación de trazas a través de request ids y event ids.
- Separación de métricas de aplicación y métricas de infraestructura.

## Componentes de observabilidad

- Logs estructurados en JSON.
- Métricas expuestas via Prometheus.
- Health endpoints para liveness y readiness.
- Tracing distribuido con Jaeger.
- Alertas basadas en métricas clave.

## Generación de información

### Microservicios
- Logs de cada request entrante y evento crítico.
- Métricas de latencia, tasa de errores, throughput y uso de recursos.
- Health checks:
  - `liveness`: servicio activo.
  - `readiness`: dependencias externas disponibles.
- Trazas que pasan por API Gateway y microservicios.

### API Gateway
- Registra tiempos de respuesta, códigos de estado y rutas.
- Exporta métricas de pets/sec, errores, latencia y retries.
- Correlaciona trazas con IDs de request.

### Kafka
- Monitorea consumo/producción de eventos.
- Métricas de lag y throughput.
- Alertas en topologías de topics saturados.

### Base de datos
- Métricas de conexiones, latencia de consultas y errores SQL.

## Qué información debe existir

- Identificador de tenant y servicio en logs.
- Evento de dominio y fase del proceso en trazas.
- Agregado afectado e identificadores clave.
- Estado de cada health check.
- Conteo de eventos producidos/consumidos.

## Relaciones

- Cada solicitud HTTP / evento Kafka debe poder trazarse desde el frontend hasta el servicio backend.
- Los logs deben incluir `trace_id`, `span_id`, `request_id`, `tenant_id`.
- Las métricas deben ser agrupadas por servicio y por tenant cuando sea relevante.

## Notas

- No se define una pila específica, sólo la arquitectura conceptual.
- La implementación concreta puede usar Prometheus, Jaeger y un log collector compatible con JSON.
