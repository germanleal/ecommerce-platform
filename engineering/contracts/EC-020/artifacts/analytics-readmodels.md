# EC-020 Parte 2 — Read Models

Los consumidores actualizan read models mediante `AnalyticsEventConsumer`. El envelope se valida por `eventId` y `tenantId`; un evento repetido se ignora. Las proyecciones de órdenes, pagos, inventario y productos son upsert por tenant y clave de negocio.

El dashboard utiliza una cache local configurable de corta duración para consultas frecuentes. La cache se invalida naturalmente por expiración y nunca es fuente de verdad.
