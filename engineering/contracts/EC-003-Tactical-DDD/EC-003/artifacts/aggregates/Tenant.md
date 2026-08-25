# Aggregate Root: Tenant

## 1. Identificación
- **Nombre**: Tenant
- **Contexto**: Provisioning & Tenant Management
- **Objetivo**: Representar la identidad y el estado de una organización cliente en la plataforma SaaS.
- **Responsabilidad principal**: Gestionar el ciclo de vida de la empresa, sus planes de suscripción y garantizar el aislamiento global de sus recursos.

## 2. Límites
- **Qué pertenece**: Información legal de la empresa, plan de suscripción actual, estado de activación, configuración global del tenant.
- **Qué NO pertenece**: Productos, pedidos, usuarios individuales (pertenecen a sus respectivos contextos).
- **Qué reglas protege**: Aislamiento de datos por `tenant_id`, límites de recursos según el plan.
- **Qué datos controla**: `tenant_id`, `legal_name`, `subscription_plan`, `status`.
- **Qué comportamiento encapsula**: Registro, cambio de plan, suspensión y terminación del servicio.

## 3. Invariantes
- **BR-MT-01**: Un Tenant debe tener al menos un administrador activo asignado en el momento de la activación.
- **BR-MT-02**: El `tenantId` es inmutable y debe ser único en toda la plataforma.
- **BR-MT-03**: El estado de un Tenant determina la disponibilidad de todas sus tiendas asociadas (si el Tenant está suspendido, sus tiendas también).

## 4. Ciclo de Vida
### Estados
- **PENDING_APPROVAL**: Recién registrado, esperando validación de datos/pago.
- **ACTIVE**: Operativo y con acceso a todas las funcionalidades del plan.
- **SUSPENDED**: Acceso bloqueado temporalmente (ej. falta de pago).
- **TERMINATED**: Cuenta cerrada, datos en proceso de archivado/borrado.

### Transiciones
- `PENDING_APPROVAL` -> `ACTIVE`: Realizado por `Platform Administrator` tras validación.
- `ACTIVE` -> `SUSPENDED`: Automático por `Policy` de pago o manual por `Platform Administrator`.
- `SUSPENDED` -> `ACTIVE`: Tras regularización de la causa de suspensión.
- `*` -> `TERMINATED`: Proceso de baja definitiva.

## 5. Responsabilidades
- **Qué hace**: Valida cambios de plan, gestiona la transición de estados de la organización.
- **Qué NO hace**: No gestiona el catálogo de productos ni procesa ventas directas.

## 6. Relaciones
- Posee una colección de `StoreInstance` (Entity) que representan las tiendas registradas.
- Se relaciona conceptualmente con el `User` (IAM) que actúa como su administrador principal.
