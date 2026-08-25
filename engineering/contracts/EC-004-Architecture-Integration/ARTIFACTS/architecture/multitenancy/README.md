# Multi-Tenancy Architecture

Este documento define la estrategia de multi-tenant para la plataforma y el modelo de datos lógico del tenant.

## Objetivo

- Definir cómo se modela el tenant y su relación con stores, usuarios y recursos.
- Establecer la resolución de tenant y el aislamiento de datos.
- Describir el rol de la plataforma, tenant y tienda.

## Principios

- Multi-tenancy es una preocupación transversal.
- Aislamiento lógico obligatorio, físico opcional.
- `tenant_id` debe ser parte de cada evento y cada request.
- El tenant se resuelve temprano en la petición.

## Modelo de entidad

- `Platform Owner`: propietario de la plataforma que administra tenants, políticas globales y operaciones del mall.
- `Tenant`: organización registrada en la plataforma. Puede contener múltiples `Stores`.
- `Store`: instancia operativa dentro de un tenant con su propio catálogo y stock.
- `Users`: usuarios asociados a un tenant y/o store. Incluye `Tenant Admin`, `Store Manager`, `Customer`.
- `Resources`: productos, inventario, pedidos, facturación y configuraciones asociadas al tenant.

## Relaciones

```
Platform Owner
  ↓
Tenant
  ↓
Store
  ↓
Users
  ↓
Resources
```

## Tenant Resolution

- El frontend o gateway obtiene el tenant context al inicio de sesión / selección de tienda.
- Cada request debe contener:
  - `tenant_id`
  - `store_id` cuando aplique
  - `user_id`
- El API Gateway valida y normaliza el `tenant_id` antes de enrutar.
- Los microservicios usan el `tenant_id` para filtrar datos y aplicar reglas de aislamiento.

## Tenant Isolation

### Nivel lógico
- Todos los datos deben incluir `tenant_id`.
- Cada servicio mantiene su propio conjunto de datos con separación por tenant.
- Los reports y búsquedas globales se basan en vistas agregadas respetando permisos.

### Nivel físico
- En la primera fase, se usa una base de datos compartida con esquemas lógicos separados.
- Cada microservicio puede tener un esquema propio dentro de PostgreSQL.
- La separación física será evaluada más adelante si el volumen o requisitos de seguridad lo requieren.

## Tenant Administration

- `Provisioning Service` es el responsable del ciclo de vida del tenant.
- Debe exponer información de estado: activo, suspendido, plan y límites de recursos.
- Debe generar eventos para que el resto de los servicios sincronice el contexto.

## Tenant Security Boundary

- El tenant boundary se aplica en el gateway y en cada servicio.
- Los tokens de usuario deben incluir`tenant_id` y roles relevantes.
- El servicio rechaza operaciones que intentan acceder a recursos de otro tenant.

## Tenant Navigation

- Marketplace global permite explorar tiendas y productos sin conocer un tenant específico.
- La selección de tienda define el tenant en la sesión del cliente.
- La experiencia separada por tenant se aplica a configuración de storefront y catálogo.

## Notas

- El administrador global de la plataforma tiene visibilidad y control sobre múltiples tenants.
- Cada tenant es dueño de sus tiendas y datos de comercio.
- Las tiendas independientes dentro de un tenant comparten políticas de tenant, pero pueden tener catálogos y precios propios.
