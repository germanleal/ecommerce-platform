# Bootstrap Traceability Matrix

| Contract | Bootstrap area | Project/module | Responsibility | Capability / bounded context |
|---|---|---|---|---|
| EC-005 | Repository | `backend/`, `frontend/`, `shared/` | Estructura física y nomenclatura | Plataforma ecommerce multi-tenant |
| EC-006 | Templates | `engineering/templates/` | Plantillas reutilizables | Consistencia de implementación |
| EC-007 | Backend | `backend/*-service/` | Superficie preparada para servicios | Capacidades futuras del dominio |
| EC-007 | Frontend | `frontend/*/` | Superficie preparada para aplicaciones | Experiencias marketplace y administración |
| EC-007 | Shared | `shared/*/` | Librerías comunes sin lógica de dominio | Reutilización transversal |
| EC-007 | Infrastructure | `infrastructure/*/` | Organización de infraestructura futura | Ejecución local y observabilidad |
| EC-007 | Engineering | `engineering/*/` | Gobierno, calidad y contexto IA | Desarrollo gobernado |
