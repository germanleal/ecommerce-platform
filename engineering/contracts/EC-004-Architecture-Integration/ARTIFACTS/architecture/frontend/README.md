# Frontend Architecture

Este documento describe la estructura frontend para la aplicación React/TypeScript del marketplace multi-tenant.

## Objetivo

- Definir la arquitectura React y los módulos del frontend.
- Establecer la comunicación con los servicios backend.
- Alinear navegación marketplace y tienda con el dominio.

## Principios

- React + TypeScript + TailwindCSS + Radix UI.
- Frontend modular basado en dominios y rutas.
- Separación entre experiencia global del marketplace y experiencia del tenant/store.
- Uso de autenticación OAuth2/OIDC con Keycloak.

## Estructura conceptual

- `src/app`: configuración global y rutas principales.
- `src/features`: módulos por dominio (auth, marketplace, store, checkout, tenant).
- `src/shared`: componentes comunes, hooks, servicios API.
- `src/lib`: utilidades, client HTTP, validadores.
- `src/styles`: Tailwind CSS.

## Módulos

- `auth`: login, logout, manejo de tokens y rutas protegidas.
- `marketplace`: búsqueda global, listado de tiendas y productos.
- `storefront`: vista de tienda específica y catálogo por tenant.
- `checkout`: flujo de carrito, pedido y pago.
- `tenant-admin`: administración de tenant, tiendas y planes.
- `profile`: gestión de perfil de usuario.

## Comunicación backend

- El frontend consume APIs a través de un API Gateway.
- Autenticación con `access_token` en `Authorization: Bearer`.
- El gateway enruta hacia los servicios correctos (`IAM`, `Provisioning`, `Store Operations`, `Order Fulfillment`, `Marketplace Discovery`, `Payments & Billing`).

## Manejo de estado

- Estado local con React Query o equivalente para caché y sincronización de datos.
- Estado global para autenticación y selección de tenant/store.
- `react-router` para navegación y rutas protegidas.
- Módulos de UI desacoplados de la lógica de negocio.

## Autenticación

- Login por Keycloak mediante OIDC.
- Refresh tokens manejados de forma segura.
- El estado de sesión almacena `tenant_id`, `store_id` y roles.
- Las rutas se protegen según roles y tipo de usuario.

## Navegación marketplace

- Página principal con catálogo global.
- Búsqueda y filtros de productos y tiendas.
- Página de detalles de tienda y productos.
- Selección de tienda como paso previo al checkout si es necesario.

## Navegación tienda

- Panel de tienda con catálogo, stock y opciones de compra.
- Experiencia separada por tenant con branding y configuraciones.
- Customer puede ver el carrito, historial de pedidos y notificaciones.
- Tenant Admin y Store Manager acceden a áreas de administración.

## Consideraciones

- El frontend no contiene lógica de dominio crítica.
- La separación de experiencia debe reflejar el modelo `Tenant -> Store -> Resources`.
- La navegación debe soportar tanto marketplace como tiendas individuales.
