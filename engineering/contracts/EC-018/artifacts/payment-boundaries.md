# Payment Boundaries

## Payment Context

### Responsabilidades

- Recibir solicitudes de pago asociadas a órdenes confirmadas.
- Crear y mantener el estado de un `Payment`.
- Registrar cada `PaymentAttempt` para las interacciones con proveedores.
- Orquestar los flujos de autorización, captura, cancelación y reembolso.
- Garantizar idempotencia y consistencia de intentos de pago.
- Emitir eventos de dominio relevantes.

### Problemas que resuelve

- Falta de un flujo claro para procesar pagos desde la orden.
- Dependencia rígida a proveedores específicos.
- Ejecuciones duplicadas de la misma transacción.
- Ausencia de historial completo de intentos de cobro.
- Inconsistencias al reconciliar pagos con órdenes.

### Capacidades

- Crear pagos basados en datos de orden y cliente.
- Registrar intentos de pago con proveedor.
- Administrar estados de pago y transiciones válidas.
- Manejar reembolsos parciales y totales.
- Exponer un modelo de gateway independiente.
- Validar el contexto del tenant en cada operación.

### Responsabilidades excluidas

- Generación de documentos fiscales o facturas legales.
- Gestión de impuestos.
- Persistencia de datos reales y detalles sensibles de tarjetas.
- Cálculo de precios o reglas comerciales.
- Modificar Order, Commerce o Marketplace.

---

## Billing Context

### Responsabilidades

- Recibir pagos exitosamente capturados y confirmados.
- Administrar el registro financiero posterior al pago.
- Generar referencias de facturación y estados de invoice.
- Mantener el historial de liquidación y ajuste financiero.
- Exponer datos a finanzas y contabilidad.

### Problemas que resuelve

- Separar el movimiento de dinero del registro financiero.
- Evitar que la facturación procese directamente pagos.
- Proveer un punto único de verdad para estados de invoice y liquidación.
- Documentar la relación entre orden, pago e invoice.

### Capacidades

- Crear y actualizar `BillingRecord` e `Invoice` tras el pago.
- Vincular invoices a `Payment` y `Order`.
- Administrar estados de facturación y liquidación.
- Registrar ajustes por reembolsos o cancelaciones.
- Exponer eventos financieros de mayor nivel.

### Responsabilidades excluidas

- Procesamiento de autorizaciones, capturas o reembolsos.
- Emisión de facturas fiscales o documentos legales.
- Integración directa con sistemas bancarios para pagos.
- Cálculo contable o tributario completo.

---

## Límites con Order

- Payments consume: Order, Commercial Snapshot y Customer.
- Payments no modifica: Order, Shopping Cart, Pricing ni ningun agregado de Commerce.
- Billing se basa en pagos finalizados y órdenes confirmadas, pero no altera la orden.

## Límites con Commerce y Marketplace

- Payments no recalcula precios ni evalúa reglas comerciales.
- Payments no posee datos de catálogo ni inventario.
- Billing no posee información de productos ni reglas de comercio.

## Modelo de consistencia

- Payment es consistente internamente con sus intentos y reembolsos.
- Billing es consistente internamente con sus invoices y estados.
- La integración con Order y proveedores es eventual a través de eventos y contratos.
