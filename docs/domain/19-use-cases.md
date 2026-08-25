# 19. Use Cases

## Objetivo
Documentar las interacciones específicas de los actores con el sistema para cumplir objetivos de negocio, trazados directamente a las capacidades.

## 1. Contexto: Provisioning

### UC-PROV-01: Registro de Nuevo Tenant
- **Objetivo**: Incorporar una nueva empresa a la plataforma.
- **Actor**: Tenant Administrator.
- **Precondiciones**: Plan comercial seleccionado.
- **Flujo Principal**:
    1. El usuario ingresa datos de la empresa.
    2. El sistema valida la unicidad del dominio.
    3. Se crea el Agregado Tenant (vía Factory).
    4. Se registra el usuario administrador en IAM.
- **Postcondiciones**: Tenant activo y entorno aprovisionado.
- **Eventos**: `TenantRegistered`.

---

## 2. Contexto: Store Operations

### UC-OPS-01: Publicar Producto
- **Objetivo**: Hacer que un producto sea visible para la venta.
- **Actor**: Store Manager.
- **Reglas**: Debe cumplir con la `ProductPublishableSpecification`.
- **Eventos**: `ProductPublished`.

---

## 3. Contexto: Order Fulfillment

### UC-FULL-01: Realizar Checkout
- **Objetivo**: Convertir una intención de compra en un pedido firme.
- **Actor**: Customer.
- **Precondiciones**: Carrito con productos, Usuario autenticado.
- **Flujo Principal**:
    1. Validación de stock disponible.
    2. Cálculo de totales y envío.
    3. Creación de la Orden (vía OrderFactory).
    4. Reserva de inventario.
- **Postcondiciones**: Orden en estado `AWAITING_PAYMENT`.
- **Eventos**: `OrderPlaced`, `StockReserved`.

