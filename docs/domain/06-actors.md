# 06. Actors

## Objetivo
Identificar a todos los individuos, sistemas o entidades que interactúan con la plataforma, definiendo sus responsabilidades, objetivos y restricciones dentro del dominio.

## Catálogo de Actores

### 1. Platform Administrator (Administrador Global)
- **Responsabilidades**: Supervisar la salud global del sistema, gestionar el catálogo de empresas (Tenants), definir planes comerciales y servicios globales.
- **Objetivos**: Mantener la estabilidad del "Mall Virtual" y asegurar la rentabilidad de la plataforma.
- **Restricciones**: No debe intervenir en la operación interna de una tienda a menos que sea por razones de soporte técnico o cumplimiento legal.
- **Capacidades utilizadas**: Multi-Tenant Orchestration, Audit & Logging, Global Metrics.

### 2. Tenant Administrator (Administrador de Empresa)
- **Responsabilidades**: Configurar la organización, gestionar usuarios administrativos internos, crear y supervisar múltiples tiendas.
- **Objetivos**: Maximizar la eficiencia operativa de su grupo de tiendas.
- **Restricciones**: Solo puede acceder a recursos pertenecientes a su `tenant_id`.
- **Capacidades utilizadas**: Multi-Tenant Orchestration (nivel Tenant), Identity & Access Management.

### 3. Store Administrator / Manager (Gestor de Tienda)
- **Responsabilidades**: Gestionar el catálogo de productos de una tienda específica, controlar inventario, procesar pedidos y configurar promociones.
- **Objetivos**: Cumplir con las metas de venta y asegurar la satisfacción del cliente de la tienda.
- **Restricciones**: Acceso limitado exclusivamente a la tienda asignada.
- **Capacidades utilizadas**: Dynamic Catalog Engine, Order Management, Inventory Control.

### 4. Customer (Cliente Autenticado)
- **Responsabilidades**: Navegar por el marketplace, gestionar su perfil, realizar compras y hacer seguimiento de sus pedidos.
- **Objetivos**: Encontrar productos de interés y realizar compras de forma segura y sencilla.
- **Restricciones**: Solo puede ver sus propios pedidos y datos personales.
- **Capacidades utilizadas**: Unified Marketplace, Order Management (Vista Cliente), Payment Integration.

### 5. Guest (Visitante)
- **Responsabilidades**: Navegar por el marketplace y tiendas públicas, buscar productos.
- **Objetivos**: Explorar la oferta del mall sin compromiso de registro inicial.
- **Restricciones**: No puede realizar compras ni acceder a áreas privadas sin autenticación.
- **Capacidades utilizadas**: Unified Marketplace.

### 6. Support Operator (Soporte Técnico)
- **Responsabilidades**: Atender incidencias reportadas por Tenants o Clientes.
- **Objetivos**: Resolver problemas técnicos o dudas funcionales en el menor tiempo posible.
- **Restricciones**: Acceso a datos de negocio solo bajo protocolos de auditoría estrictos.
- **Capacidades utilizadas**: Audit & Logging, Support Tools.

### 7. External Systems (Sistemas Externos)
- **Descripción**: Pasarelas de pago (Stripe, PayPal), servicios logísticos (FedEx, DHL), servicios de notificación (SendGrid).
- **Interacciones**: Procesamiento de pagos, actualización de estados de envío, entrega de mensajes.
- **Capacidades utilizadas**: Payment Integration, Notification Engine.

## Matriz de Interacción Actor-Capacidad

| Actor | Marketplace | Catalog | Orders | Payments | Identity |
| :--- | :---: | :---: | :---: | :---: | :---: |
| Platform Admin | R | R | R | R | A |
| Tenant Admin | - | R | R | - | A |
| Store Manager | - | A | A | - | - |
| Customer | A | A | A | A | A |
| Guest | A | A | - | - | - |

*(A: Acceso Activo, R: Revisión/Supervisión)*

## Referencias Cruzadas
- Ver [03. Business Processes](file:///D:/personales/development/ecommerce-platform/docs/domain/03-business-processes.md) para ver cómo estos actores participan en los flujos de negocio.
