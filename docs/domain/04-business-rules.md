# 04. Business Rules

## Objetivo
Documentar de forma explícita y unívoca las reglas de negocio que rigen el comportamiento de la plataforma, asegurando que la lógica de dominio sea consistente en todos los contextos.

## Catálogo de Reglas de Negocio

### 1. Multi-Tenancy & Aislamiento

| Código | Nombre | Descripción | Justificación | Contexto |
| :--- | :--- | :--- | :--- | :--- |
| **BR-MT-01** | Aislamiento de Datos | Ninguna operación de lectura o escritura puede omitir el filtro por `tenant_id`. | Garantizar la privacidad y seguridad de cada empresa. | Global |
| **BR-MT-02** | Unicidad de Dominio | El dominio o subdominio de una tienda debe ser único en toda la plataforma. | Evitar colisiones en la resolución de URLs y branding. | Provisioning |
| **BR-MT-03** | Límites de Suscripción | El número de tiendas y usuarios está limitado por el plan del Tenant. | Controlar el uso de recursos y monetización. | Subscription |

### 2. Catálogo e Inventario

| Código | Nombre | Descripción | Justificación | Contexto |
| :--- | :--- | :--- | :--- | :--- |
| **BR-CAT-01** | Stock Mínimo | No se permite la venta de productos con stock cero a menos que se active el modo "Pre-venta". | Evitar insatisfacción por falta de cumplimiento. | Inventory |
| **BR-CAT-02** | Variantes de Producto | Todo producto debe tener al menos una variante (ej. Talla única) para ser publicado. | Mantener consistencia en el modelo de precios y SKU. | Catalog |
| **BR-CAT-03** | Visibilidad de Precios | Los precios deben mostrarse siempre con los impuestos incluidos o desglosados según la región del Tenant. | Cumplimiento legal y transparencia. | Catalog |

### 3. Pedidos y Transacciones

| Código | Nombre | Descripción | Justificación | Contexto |
| :--- | :--- | :--- | :--- | :--- |
| **BR-ORD-01** | Reserva de Stock | El stock se reserva temporalmente al iniciar el checkout y se confirma al aprobar el pago. | Prevenir sobreventa durante el proceso de pago. | Orders |
| **BR-ORD-02** | Cancelación de Pedido | Un cliente solo puede cancelar un pedido si este aún no ha entrado en estado de "Preparación". | Evitar conflictos logísticos y costos de operación. | Orders |
| **BR-ORD-03** | Unicidad de Transacción | Un pedido solo puede ser pagado una vez; reintentos deben usar la misma referencia de pedido. | Prevenir cobros duplicados. | Payments |

### 4. Clientes y Acceso

| Código | Nombre | Descripción | Justificación | Contexto |
| :--- | :--- | :--- | :--- | :--- |
| **BR-IAM-01** | Mínimo Privilegio | Los usuarios solo tienen acceso a las funcionalidades explícitamente permitidas por su rol. | Seguridad interna y control operativo. | Security |
| **BR-IAM-02** | Sesión Única | (Opcional) Un cliente solo puede tener una sesión de checkout activa por tienda. | Evitar inconsistencias en el carrito y stock. | Checkout |

## Matriz de Impacto de Reglas

| Regla | Criticidad | Actor Afectado | Riesgo de Incumplimiento |
| :--- | :--- | :--- | :--- |
| BR-MT-01 | Crítica | Todos | Fuga masiva de datos, demanda legal. |
| BR-ORD-01 | Alta | Cliente / Tienda | Sobreventa, pérdida de reputación. |
| BR-CAT-03 | Media | Cliente | Problemas legales regionales. |

## Referencias Cruzadas
- Ver [05. Domain Events](file:///D:/personales/development/ecommerce-platform/docs/domain/05-domain-events.md) para ver qué eventos disparan o validan estas reglas.
