# ADR 0011: Priorización de Tecnologías Open Source

## Estado
Aceptado

## Contexto
Queremos evitar el bloqueo por parte de proveedores y reducir los costos de licenciamiento iniciales.

## Problema
¿Cuál debe ser el criterio de selección de herramientas y librerías?

## Alternativas Consideradas
- **Cloud Native Services (AWS/Azure)**: Muy potentes pero generan dependencia total del proveedor (Vendor Lock-in).
- **Open Source First**: Flexibilidad total y transparencia.

## Decisión
Adoptar una política de "Open Source First". Solo se considerarán alternativas propietarias si no existe una opción open source viable que cumpla con los requerimientos técnicos.

## Consecuencias
- **Positivas**: Sin costos de licencia iniciales, mayor control sobre la tecnología, comunidad activa.
- **Negativas**: Responsabilidad propia sobre la actualización y mantenimiento de las herramientas.

## Impacto Futuro
Garantiza que la plataforma pueda ser desplegada en cualquier nube o infraestructura on-premise sin cambios estructurales.
