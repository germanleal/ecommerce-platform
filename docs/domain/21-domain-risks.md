# 21. Domain Risks

## 1. Riesgos de Consistencia
- **Riesgo**: Desincronización entre el stock real y el stock mostrado en el Marketplace (Discovery) debido a la consistencia eventual.
- **Impacto**: Alto (Ventas fallidas, mala experiencia).
- **Mitigación**: Implementar políticas de "Stock Buffer" y notificaciones de alta prioridad para actualizaciones de inventario.

## 2. Riesgos Multi-Tenant
- **Riesgo**: "Vecino ruidoso" (un tenant consume excesivos recursos de base de datos afectando a otros).
- **Impacto**: Medio-Alto.
- **Mitigación**: Implementar cuotas y límites a nivel de aplicación y monitoreo por `tenant_id`.

## 3. Riesgos de Integración
- **Riesgo**: Fallos en la pasarela de pagos externa durante el cierre de una orden.
- **Impacto**: Crítico.
- **Mitigación**: Implementar estados intermedios de pago y procesos de conciliación asíncrona.

