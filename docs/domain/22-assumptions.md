# 22. Assumptions

## Objetivo
Documentar explícitamente todos los supuestos realizados durante el modelado para su validación futura.

## 1. Moneda y Región
- **Supuesto**: Cada Tienda opera en una única moneda base definida en su configuración inicial.
- **Justificación**: Simplifica la lógica de precios y contabilidad en el MVP.
- **Riesgo**: Puede limitar a tenants que operan en zonas fronterizas o internacionales con una sola tienda.

## 2. Identidad de Usuario
- **Supuesto**: Un usuario se identifica globalmente por su email en IAM, pero su perfil de negocio y permisos son específicos por `tenant_id`.
- **Justificación**: Permite un SSO (Single Sign-On) técnico mientras se mantiene el aislamiento de negocio.

## 3. Pagos Externos
- **Supuesto**: La pasarela de pagos es responsable de la seguridad de la tarjeta y el cumplimiento de PCI. La plataforma solo almacena tokens de referencia.
