# 10. Ubiquitous Language

## Objetivo
Establecer un lenguaje común y riguroso que sea utilizado por expertos de negocio, desarrolladores y agentes de IA, eliminando ambigüedades en la comunicación.

## Términos Centrales del Dominio

### 1. Nivel Plataforma (Global)
- **Tenant (Inquilino)**: Representa a la Empresa u Organización que contrata la plataforma. Es la unidad máxima de aislamiento.
    - *Contexto*: Provisioning.
    - *Prohibido*: Empresa, Cliente Corporativo, Cuenta.
- **Platform Administrator**: Rol con control total sobre la infraestructura y los tenants.
    - *Contexto*: Global.

### 2. Nivel Operativo (Tienda)
- **Store (Tienda)**: Unidad de venta con identidad propia perteneciente a un Tenant.
    - *Contexto*: Store Operations.
    - *Prohibido*: Sucursal, Local, Shop.
- **Product (Producto)**: Definición abstracta de un item en venta (ej. Camiseta Polo).
    - *Contexto*: Store Operations.
- **Variant (Variante)**: Instancia específica de un producto con atributos propios (ej. Camiseta Polo Azul Talla L).
    - *Contexto*: Store Operations.
- **SKU (Stock Keeping Unit)**: Código único identificador de una variante para control de inventario.
    - *Contexto*: Inventory.

### 3. Nivel Transaccional (Ventas)
- **Order (Pedido)**: Contrato de compra entre un Cliente y una Tienda.
    - *Contexto*: Order Fulfillment.
    - *Prohibido*: Factura (Invoice es post-pago), Compra, Ticket.
- **Line Item**: Una línea dentro de un pedido que referencia a un SKU, cantidad y precio capturado.
    - *Contexto*: Order Fulfillment.
- **Checkout**: Proceso de finalizar la selección de productos y proceder al pago.
    - *Contexto*: Checkout.

### 4. Nivel Usuario
- **Customer (Cliente)**: Persona que compra en el marketplace o tiendas.
    - *Contexto*: Global / Discovery.
    - *Prohibido*: Usuario (User es el término técnico de IAM).

## Reglas de Uso del Lenguaje
1. **No Sinónimos**: Si el término es `Order`, nunca se debe usar `Pedido` en el código o conversaciones técnicas.
2. **Contexto Obligatorio**: Un término puede significar cosas distintas en contextos distintos (ej. `Product` en Discovery es un resumen visual, en Operations es una entidad compleja).
3. **Inglés en el Código, Español en el Análisis**: (Según definiciones de Fase 0).

## Referencias Cruzadas
- Ver [11. Domain Glossary](file:///D:/personales/development/ecommerce-platform/docs/domain/11-domain-glossary.md) para definiciones técnicas extendidas.
