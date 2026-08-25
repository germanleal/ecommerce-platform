# 05. Domain Events

## Objetivo
Identificar los eventos significativos del negocio que representan cambios de estado o hitos importantes en el dominio, sirviendo como base para la comunicación asíncrona y el desacoplamiento.

## Catálogo de Eventos de Dominio

### 1. Gestión de Plataforma y Tenants
| Evento | Descripción | Actor Originador | Consecuencia |
| :--- | :--- | :--- | :--- |
| **TenantRegistered** | Una nueva empresa se ha registrado exitosamente. | Tenant Admin | Creación de entorno, envío de bienvenida. |
| **TenantSuspended** | El acceso de una empresa ha sido revocado. | Platform Admin | Bloqueo de login para todos los usuarios del tenant. |
| **StoreCreated** | Se ha instanciado una nueva tienda dentro de un tenant. | Tenant Admin | Habilitación de configuración de catálogo. |

### 2. Catálogo e Inventario
| Evento | Descripción | Actor Originador | Consecuencia |
| :--- | :--- | :--- | :--- |
| **ProductPublished** | Un producto ya está visible para los clientes. | Store Manager | Actualización del índice de búsqueda del marketplace. |
| **StockLevelLow** | El stock de un producto ha caído por debajo del umbral definido. | System (Inventory) | Notificación al Store Manager para reposición. |
| **StockReserved** | Unidades de stock bloqueadas temporalmente por un checkout. | System (Checkout) | Disminución del stock disponible para otros clientes. |

### 3. Ventas y Pedidos
| Evento | Descripción | Actor Originador | Consecuencia |
| :--- | :--- | :--- | :--- |
| **OrderPlaced** | Un cliente ha confirmado su intención de compra. | Customer | Inicio del flujo de pago y reserva definitiva de stock. |
| **PaymentApproved** | El sistema de pago ha confirmado la recepción de fondos. | External System | El pedido pasa a estado "Pagado" y se inicia fulfillment. |
| **OrderCancelled** | El pedido ha sido anulado antes de su despacho. | Customer / Manager | Liberación de stock y gestión de reembolso. |
| **OrderShipped** | El paquete ha salido del almacén de la tienda. | Store Manager | Notificación de seguimiento al cliente. |

### 4. Cliente y Marketing
| Evento | Descripción | Actor Originador | Consecuencia |
| :--- | :--- | :--- | :--- |
| **CustomerRegistered** | Un nuevo usuario ha creado su cuenta. | Customer | Creación de perfil, posible asignación de cupones. |
| **CouponApplied** | Se ha validado y aplicado un descuento a un carrito. | Customer | Recálculo de totales del pedido. |

## Modelo de Evento (Conceptual)
Cada evento de dominio debe contener al menos:
- `eventId`: Identificador único (UUID).
- `occurredOn`: Timestamp del momento del evento.
- `tenantId`: Contexto de la empresa.
- `storeId`: Contexto de la tienda (si aplica).
- `aggregateId`: ID de la entidad principal afectada (ej. orderId).
- `payload`: Datos específicos del cambio (ej. estado anterior y nuevo).

## Relación con Procesos
- **Proceso de Venta**: `OrderPlaced` -> `PaymentApproved` -> `OrderShipped`.
- **Proceso de Inventario**: `StockReserved` -> `StockDepleted` (si se agota).

## Referencias Cruzadas
- Ver [03. Business Processes](file:///D:/personales/development/ecommerce-platform/docs/domain/03-business-processes.md) para ver en qué punto de los flujos se disparan estos eventos.
