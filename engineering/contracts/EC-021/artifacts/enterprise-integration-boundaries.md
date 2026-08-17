# Enterprise Integration Boundaries

Enterprise Integration consume APIs/eventos públicos de Marketplace, Commerce, Order, Payments, Inventory y Analytics. No accede a tablas de otros servicios, no crea FK externas y no ejecuta lógica transaccional de esos dominios.

## Contextos

- **Enterprise Integration Context:** gobierno de integraciones y auditoría.
- **Connector Context:** registro, configuración, estado y versión del proveedor.
- **Adapter Context:** ports, adapters, mappers y transformadores.
- **Synchronization Context:** ejecución y coordinación de sincronizaciones.

Los proveedores SAP, Dynamics, Salesforce, Shopify, storage y mensajería son futuros adapters reemplazables.
