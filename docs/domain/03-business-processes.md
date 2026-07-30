# 03. Business Processes

## Objetivo
Identificar y describir los procesos de negocio fundamentales que ocurren dentro de la plataforma, detallando los pasos, actores involucrados y las capacidades que los habilitan.

## Catálogo de Procesos de Negocio

### 1. Onboarding de Empresa (Tenant Registration)
Proceso mediante el cual una nueva organización se une a la plataforma.
- **Actor Principal**: Tenant Administrator.
- **Pasos**:
    1. Solicitud de registro con datos legales y comerciales.
    2. Selección de plan y método de facturación.
    3. Verificación de identidad (opcional/automática).
    4. Creación del espacio de trabajo del Tenant (`tenant_id`).
    5. Configuración del administrador inicial.
- **Capacidades**: Multi-Tenant Orchestration, Identity & Access Management.

### 2. Creación y Configuración de Tienda
Proceso de instanciar una unidad de venta dentro de una empresa.
- **Actor Principal**: Tenant Administrator.
- **Pasos**:
    1. Definición de nombre y dominio/subdominio de la tienda.
    2. Configuración de identidad visual (logos, colores).
    3. Configuración de parámetros regionales (moneda, idioma).
    4. Asignación de Store Managers.
- **Capacidades**: Storefront Customization, Multi-Tenant Orchestration.

### 3. Gestión de Catálogo y Publicación
Proceso de preparar la oferta de productos para los clientes.
- **Actor Principal**: Store Manager.
- **Pasos**:
    1. Creación de categorías y atributos.
    2. Carga de productos y variantes.
    3. Gestión de assets (imágenes, videos).
    4. Definición de precios y reglas de impuestos.
    5. Publicación en la tienda.
- **Capacidades**: Dynamic Catalog Engine, Digital Asset Management (DAM).

### 4. Ciclo de Compra y Pago (Checkout)
Proceso transaccional del cliente final.
- **Actor Principal**: Customer.
- **Pasos**:
    1. Selección de productos y adición al carrito.
    2. Revisión de pedido y cálculo de envío/impuestos.
    3. Selección de método de pago.
    4. Ejecución del pago a través de pasarela externa.
    5. Confirmación del pedido y reserva de stock.
- **Capacidades**: Unified Marketplace, Payment Integration, Inventory Control, Order Management.

### 5. Cumplimiento de Pedido (Order Fulfillment)
Proceso operativo post-venta.
- **Actor Principal**: Store Manager.
- **Pasos**:
    1. Recepción y validación del pedido.
    2. Preparación de productos (Picking & Packing).
    3. Generación de etiqueta de envío.
    4. Notificación al cliente sobre el despacho.
    5. Marcación como entregado.
- **Capacidades**: Order Management, Inventory Control, Notification Engine.

## Resumen de Procesos y Flujo de Eventos

| Proceso | Evento de Inicio | Evento Final |
| :--- | :--- | :--- |
| Onboarding Tenant | Solicitud Recibida | Tenant Activado |
| Configuración Tienda | Tienda Creada | Tienda Publicada |
| Gestión Catálogo | Producto Creado | Producto Disponible |
| Venta (Checkout) | Carrito Confirmado | Pedido Pagado |
| Cumplimiento | Pedido Pagado | Pedido Entregado |

## Referencias Cruzadas
- Ver [05. Domain Events](file:///D:/personales/development/ecommerce-platform/docs/domain/05-domain-events.md) para el detalle de los eventos generados en estos procesos.
- Ver [04. Business Rules](file:///D:/personales/development/ecommerce-platform/docs/domain/04-business-rules.md) para las reglas que rigen estos pasos.
