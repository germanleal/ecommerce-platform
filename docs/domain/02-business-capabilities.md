# 02. Business Capability Map

## Objetivo
Mapear las capacidades del negocio que la plataforma debe poseer para cumplir con su propósito, clasificándolas según su importancia estratégica para el dominio.

## Clasificación de Capacidades (DDD)

### 1. Core Capabilities (Diferenciadores Clave)
Capacidades que representan el valor único de la plataforma y donde reside la complejidad del negocio.

| Capability | Descripción | Objetivo | Tipo | Prioridad |
| :--- | :--- | :--- | :--- | :--- |
| **Multi-Tenant Orchestration** | Gestión del ciclo de vida de los tenants y aislamiento de recursos. | Garantizar la independencia y seguridad entre empresas. | Core | Alta |
| **Unified Marketplace** | Motor de descubrimiento, búsqueda y agregación de tiendas/productos. | Facilitar la conexión entre clientes y tiendas del mall. | Core | Alta |
| **Order Management System (OMS)** | Gestión del ciclo de vida completo del pedido desde la creación hasta la entrega. | Asegurar el cumplimiento eficiente de las ventas. | Core | Alta |
| **Dynamic Catalog Engine** | Gestión de productos, variantes, categorías y precios por tienda. | Permitir catálogos altamente personalizables por cada tenant. | Core | Alta |

### 2. Supporting Capabilities (Apoyo al Dominio)
Capacidades necesarias para que el Core funcione, pero que no son el diferenciador principal.

| Capability | Descripción | Objetivo | Tipo | Prioridad |
| :--- | :--- | :--- | :--- | :--- |
| **Inventory Control** | Seguimiento de stock en tiempo real por tienda y almacén. | Evitar ventas sin stock y gestionar reposiciones. | Supporting | Media |
| **Storefront Customization** | Herramientas para definir la identidad visual y layout de cada tienda. | Permitir que cada tienda tenga su propio look & feel. | Supporting | Media |
| **Promotion & Loyalty** | Gestión de cupones, descuentos y programas de puntos. | Incentivar las ventas y fidelizar clientes. | Supporting | Media |

### 3. Generic Capabilities (Funcionalidades Estándar)
Funcionalidades comunes a cualquier sistema empresarial que pueden ser resueltas con soluciones de mercado o estándares.

| Capability | Descripción | Objetivo | Tipo | Prioridad |
| :--- | :--- | :--- | :--- | :--- |
| **Identity & Access Management** | Autenticación y autorización de usuarios (IAM). | Garantizar acceso seguro basado en roles. | Generic | Alta |
| **Payment Integration** | Conexión con pasarelas de pago externas. | Procesar transacciones financieras de forma segura. | Generic | Alta |
| **Notification Engine** | Envío de emails, SMS y notificaciones push. | Mantener informados a los actores sobre eventos clave. | Generic | Baja |
| **Audit & Logging** | Registro de actividades para cumplimiento y depuración. | Proporcionar trazabilidad de acciones críticas. | Generic | Media |

## Detalle de Capacidades Seleccionadas (Ejemplo)

### Capability: Multi-Tenant Orchestration
- **Propósito**: Centralizar el control de las organizaciones que operan en el mall.
- **Alcance**: Registro de empresa, configuración de límites, gestión de suscripciones a la plataforma.
- **Actor Principal**: Platform Administrator.
- **Actores Secundarios**: Tenant Administrator.
- **Entradas**: Datos legales de empresa, plan seleccionado.
- **Salidas**: ID de Tenant único, entorno aprovisionado.
- **Reglas del Negocio**: Una empresa no puede acceder a los datos de otra; el tenant_id es obligatorio en toda operación.
- **Indicadores de Éxito**: Tiempo de onboarding < 5 min; 0 incidentes de fuga de datos entre tenants.

## Matriz de Capacidades

| ID | Capability | Tipo | Prioridad | Responsable |
| :--- | :--- | :--- | :--- | :--- |
| C-01 | Multi-Tenant Orchestration | Core | Alta | Platform Architect |
| C-02 | Unified Marketplace | Core | Alta | Product Manager |
| C-03 | Dynamic Catalog Engine | Core | Alta | Store Manager |
| C-04 | Order Management | Core | Alta | Operations Manager |
| C-05 | Identity & Access | Generic | Alta | Security Lead |

## Referencias Cruzadas
- Ver [07. Subdomains](file:///D:/personales/development/ecommerce-platform/docs/domain/07-subdomains.md) para ver cómo estas capacidades se agrupan en subdominios.
