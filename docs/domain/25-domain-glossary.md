# 11. Domain Glossary

## Objetivo
Proporcionar definiciones precisas y ejemplos de los términos técnicos y de negocio utilizados en el modelado del dominio.

| Término | Definición | Ejemplo | Contexto |
| :--- | :--- | :--- | :--- |
| **ACL (Anti-Corruption Layer)** | Capa que traduce modelos entre contextos para evitar que uno contamine al otro. | El traductor de Catálogo a Pedidos. | Architecture |
| **Aggregate ID** | Identificador único de la raíz de un agregado (ej. `order_id`). | `ORD-2026-X123` | Tactical (Preview) |
| **Availability** | Cantidad de stock que puede ser vendida efectivamente (Stock Físico - Reservas). | 10 unidades disponibles. | Inventory |
| **Bounded Context** | Límite explícito donde un modelo de dominio es aplicable. | Order Fulfillment. | Strategic DDD |
| **Fulfillment** | Conjunto de procesos para entregar un pedido al cliente. | Picking, Packing, Shipping. | Operations |
| **Identity Token** | Artefacto de seguridad (JWT) que contiene la identidad y claims del usuario. | `eyJhbGciOiJIUzI1Ni...` | IAM |
| **Multi-Tenancy** | Arquitectura donde una sola instancia de software sirve a múltiples clientes (Tenants). | El "Mall Virtual". | Infrastructure |
| **Orchestration** | Coordinación centralizada de múltiples servicios para completar un proceso. | Orquestación de Pedido y Pago. | Architecture |
| **Published Language** | Modelo de datos compartido y documentado para la integración entre sistemas. | Esquema JSON de un Evento. | Integration |
| **Tenant Isolation** | Garantía de que los datos y procesos de un tenant son invisibles para otros. | Filtro por `tenant_id`. | Security |

## Términos Prohibidos (Sinónimos a evitar)
- **Empresa**: Usar siempre **Tenant**.
- **Sucursal**: Usar siempre **Store**.
- **Articulo**: Usar siempre **Product** o **SKU**.
- **Carrito**: Usar siempre **Cart** o **Checkout Context**.

## Referencias Cruzadas
- Ver [10. Ubiquitous Language](file:///D:/personales/development/ecommerce-platform/docs/domain/10-ubiquitous-language.md) para el uso conversacional.
