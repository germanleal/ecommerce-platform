# Aggregate Root: User

## 1. Identificación
- **Nombre**: User
- **Contexto**: Identity & Access (IAM)
- **Objetivo**: Representar la identidad de una persona en la plataforma.
- **Responsabilidad principal**: Gestionar credenciales, perfiles y la vinculación de seguridad con los Tenants.

## 2. Límites
- **Qué pertenece**: Email, contraseña (hash), nombre, apellidos, roles globales, roles por tenant.
- **Qué NO pertenece**: Actividad transaccional de compras (pertenece al Customer en otros contextos).
- **Qué reglas protege**: Unicidad de identidad, seguridad de acceso.
- **Qué datos controla**: `user_id`, `email`, `profile_data`, `tenant_assignments`.

## 3. Invariantes
- **BR-IAM-01**: El email debe ser único en toda la plataforma.
- **BR-IAM-02**: Un usuario debe tener al menos un rol asignado para operar.

## 4. Ciclo de Vida
- **ACTIVE**: Puede loguearse y operar.
- **LOCKED**: Acceso bloqueado por demasiados intentos fallidos o seguridad.
- **DEACTIVATED**: Baja del sistema.

## 5. Responsabilidades
- **Qué hace**: Autentica y autoriza acciones basadas en roles.
- **Qué NO hace**: No conoce la lógica de negocio de los pedidos ni catálogos.
