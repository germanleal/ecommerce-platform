# 07. Subdomains

## Objetivo
Descomponer el dominio del "Mall Virtual" en subdominios manejables, clasificándolos según su valor estratégico para la plataforma.

## Clasificación de Subdominios

### 1. Core Subdomains (El Corazón del Negocio)
Donde reside la ventaja competitiva y la lógica más compleja y personalizada.

- **Multi-Tenant Management**:
    - **Objetivo**: Gestionar el ciclo de vida y aislamiento de las organizaciones.
    - **Responsabilidad**: Aprovisionamiento, límites de planes, aislamiento de datos global.
    - **Capacidades**: Multi-Tenant Orchestration.
- **Unified Marketplace Discovery**:
    - **Objetivo**: Conectar oferta (tiendas) con demanda (clientes).
    - **Responsabilidad**: Búsqueda global, agregación de catálogos, recomendaciones.
    - **Capacidades**: Unified Marketplace.
- **Distributed Order Fulfillment**:
    - **Objetivo**: Gestionar transacciones complejas a través de múltiples tiendas.
    - **Responsabilidad**: Ciclo de vida del pedido, estados, orquestación de pagos.
    - **Capacidades**: Order Management.

### 2. Supporting Subdomains (Soporte Específico)
Funcionalidades necesarias para el core, pero que no son diferenciadores por sí mismas.

- **Dynamic Cataloging**:
    - **Objetivo**: Permitir a cada tienda definir su oferta de productos.
    - **Responsabilidad**: Estructura de categorías, atributos, variantes, carga de medios.
    - **Capacidades**: Dynamic Catalog Engine.
- **Inventory & Stock Tracking**:
    - **Objetivo**: Controlar la disponibilidad física de los productos.
    - **Responsabilidad**: Niveles de stock, reservas, alertas de bajo stock.
    - **Capacidades**: Inventory Control.
- **Storefront Branding**:
    - **Objetivo**: Personalizar la experiencia visual de cada tienda.
    - **Responsabilidad**: Temas, layouts, logos, dominios personalizados.
    - **Capacidades**: Storefront Customization.

### 3. Generic Subdomains (Funcionalidad Estándar)
Problemas resueltos que se encuentran en casi cualquier software empresarial.

- **Identity & Access (IAM)**:
    - **Objetivo**: Gestionar quién puede hacer qué.
    - **Responsabilidad**: Autenticación, roles, permisos.
    - **Capacidades**: IAM.
- **Payments Gateway**:
    - **Objetivo**: Facilitar transacciones monetarias.
    - **Responsabilidad**: Integración con Stripe, PayPal, gestión de reembolsos.
    - **Capacidades**: Payment Integration.
- **Communications & Notifications**:
    - **Objetivo**: Mantener informados a los usuarios.
    - **Responsabilidad**: Envío de correos, notificaciones push.
    - **Capacidades**: Notification Engine.

## Resumen de Clasificación

| Subdominio | Clasificación | Justificación |
| :--- | :--- | :--- |
| Multi-Tenant Management | Core | Es la base del modelo SaaS y garantiza la seguridad y escalabilidad. |
| Marketplace Discovery | Core | Es el valor agregado para atraer clientes a las tiendas de los tenants. |
| Order Fulfillment | Core | La transaccionalidad robusta es vital para la confianza del mall. |
| Cataloging | Supporting | Esencial para vender, pero es un problema bien entendido. |
| IAM | Generic | Se delegará en Keycloak (estándar de la industria). |
| Payments | Generic | Se integrará con proveedores externos. |

## Referencias Cruzadas
- Ver [08. Bounded Contexts](file:///D:/personales/development/ecommerce-platform/docs/domain/08-bounded-contexts.md) para ver cómo estos subdominios se mapean a contextos de software.
