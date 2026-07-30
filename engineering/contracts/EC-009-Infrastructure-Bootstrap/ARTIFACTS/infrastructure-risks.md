# EC-009 Infrastructure Risks

| ID | Riesgo | Impacto | Probabilidad | Mitigación | Responsable |
|---|---|---|---|---|---|
| R-009-01 | Configuración inconsistente | Alto | Media | Validación Compose y revisión arquitectónica | Infrastructure Architect |
| R-009-02 | Exposición accidental de puertos | Alto | Media | Redes aisladas y revisión de Compose | DevSecOps Architect |
| R-009-03 | Secretos versionados | Crítico | Baja | `.env.example` sin valores sensibles y revisión automática | Security Architect |
| R-009-04 | Volúmenes huérfanos | Medio | Baja | Matriz de volúmenes y auditoría | Docker Specialist |
| R-009-05 | IA crea servicios no aprobados | Alto | Media | AI_CONTEXT y validación previa | AI Software Engineering Specialist |
