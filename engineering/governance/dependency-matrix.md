# Engineering Contracts Dependency Matrix

| ID | Nombre | Entradas | Consumidores | Riesgo |
|---|---|---|---|---|
| EC-000 | Project Charter | Negocio | EC-001 | Bajo |
| EC-001 | Product Discovery | EC-000 | EC-002 | Medio |
| EC-002 | Strategic DDD | EC-001 | EC-003 | Alto |
| EC-003 | Tactical DDD | EC-002 | EC-004 | Alto |
| EC-004 | Integration | EC-003 | EC-005, EC-006 | Medio |
| EC-005 | Repository | EC-004 | EC-006 | Medio |
| EC-006 | Templates | EC-005 | EC-007 | Bajo |
| EC-007 | Bootstrap | EC-006 | EC-008 | Bajo |
| EC-008 | Shared Platform Libraries | EC-000–EC-007 | EC-009 | Medio |
| EC-009 | Infrastructure Bootstrap | EC-000–EC-008 | EC-010, EC-013 | Medio |
| EC-010 | Identity & Multi-Tenant Foundation | EC-009 | EC-013, EC-014 | Alto |
| EC-013 | Infrastructure & Security | EC-009, EC-010 | EC-014 | Alto |
| EC-014 | Marketplace Storefront | EC-010, EC-013 | EC-015 | Crítico |
| EC-015 | AI Integration | EC-014 | Plataforma final | Medio |
