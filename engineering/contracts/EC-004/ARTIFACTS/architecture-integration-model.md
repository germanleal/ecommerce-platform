# EC-004 — Modelo de integración

Los bounded contexts mantienen ownership de sus entidades y base de datos. Las dependencias se expresan mediante APIs o eventos, nunca mediante acceso directo a tablas de otro servicio.

```text
Identity/Tenant -> API Gateway -> canales síncronos
Catalog/Product/Pricing/Promotion -> Search/Commerce
Cart -> Checkout -> Order -> Payment
Inventory -> Shipping/Fulfillment
Notification/Integration <- eventos de negocio
Audit/Analytics <- eventos y telemetría publicada
AI Gateway <- datos explícitamente autorizados y minimizados
```

Reglas: cada contexto publica sólo hechos que posee; los consumidores dependen del contrato; comandos reintentables son idempotentes; eventos son inmutables y versionados; la autorización se evalúa en el servicio propietario.
