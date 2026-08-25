# 01. Domain Discovery

## Objetivo
Analizar y comprender el ecosistema de negocio del "Mall Virtual" SaaS para identificar sus componentes esenciales, dinámicas de interacción y reglas fundamentales antes de proceder al modelado técnico.

## Alcance
Este análisis abarca desde la administración global de la plataforma hasta la experiencia final del cliente en una tienda específica, considerando la naturaleza Multi-Tenant del sistema.

## Análisis del Dominio: El Mall Virtual

### 1. Concepto Central
La plataforma actúa como un facilitador de infraestructura para que múltiples organizaciones (**Tenants**) operen sus propios negocios de comercio electrónico de forma independiente pero compartiendo una base tecnológica común.

### 2. Dinámica Multi-Tenant
- **Nivel Plataforma**: Control global, facturación a empresas, gestión de infraestructura.
- **Nivel Tenant (Empresa)**: Gestión de la organización, múltiples tiendas, usuarios administrativos.
- **Nivel Store (Tienda)**: Identidad visual, catálogo propio, gestión de inventario y pedidos específicos.
- **Nivel Cliente**: Navegación, búsqueda y transaccionalidad dentro del marketplace o tiendas específicas.

### 3. Actores Identificados (Resumen)
- **Administrador de Plataforma**: Gestiona el ecosistema global.
- **Administrador de Empresa (Tenant Admin)**: Gestiona la organización y sus tiendas.
- **Gestor de Tienda (Store Manager)**: Opera el día a día de una tienda específica.
- **Cliente (Customer)**: Usuario final que realiza compras.
- **Visitante (Guest)**: Usuario no autenticado que navega.

### 4. Procesos Críticos
- Onboarding de Empresas y Tiendas.
- Gestión de Catálogos Multidimensionales.
- Ciclo de Vida del Pedido (Order Management).
- Checkout Seguro y Aislamiento de Transacciones.

### 5. Riesgos del Negocio
- **Fuga de Datos**: Acceso accidental a información de otro tenant.
- **Escalabilidad**: Degradación de performance por un tenant con alta carga afectando a otros.
- **Inconsistencia de Catálogo**: Problemas en la sincronización de stock y precios.

## Motivación del Modelado
Necesitamos un modelo que permita la independencia operativa de cada tienda mientras se mantiene la eficiencia de una plataforma centralizada. El uso de DDD estratégico es vital para asegurar que los límites de cada contexto (ej. Identidad vs. Ventas) estén claramente definidos.

## Referencias Cruzadas
- Ver [02. Business Capability Map](file:///D:/personales/development/ecommerce-platform/docs/domain/02-business-capability-map.md) para el detalle de capacidades.
- Ver [06. Actors](file:///D:/personales/development/ecommerce-platform/docs/domain/06-actors.md) para el detalle de responsabilidades.
