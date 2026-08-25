# 15. Domain Policies

## Objetivo
Documentar las políticas de negocio que determinan el comportamiento automático del sistema ante ciertos eventos o condiciones.

## Catálogo de Políticas

### 1. Política de Suspensión de Tenant
- **Objetivo**: Bloquear el acceso a una empresa ante incumplimiento de pago o violación de términos.
- **Regla**: Si un Tenant pasa a estado `SUSPENDED`, todos los procesos de `Order` en curso de sus tiendas deben ser pausados y el acceso a `IAM` revocado para sus usuarios.
- **Contexto**: Provisioning / Global.

### 2. Política de Reposición de Inventario
- **Objetivo**: Asegurar la disponibilidad de productos críticos.
- **Regla**: Al dispararse el evento `StockLevelLow`, el sistema debe generar automáticamente una alerta al `Store Manager` y, si está configurado, crear un borrador de orden de compra al proveedor.
- **Contexto**: Inventory.

### 3. Política de Reembolso Automático
- **Objetivo**: Mejorar la experiencia del cliente ante cancelaciones.
- **Regla**: Si un pedido es cancelado antes de ser "Preparado" y ya fue pagado, se debe disparar automáticamente el proceso de reembolso en el BC de Payments.
- **Contexto**: Order Fulfillment / Payments.

