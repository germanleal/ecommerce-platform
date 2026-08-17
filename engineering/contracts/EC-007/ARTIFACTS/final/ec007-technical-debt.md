# EC-007 Technical Debt

| Prioridad | Deuda | Impacto | Recomendación |
|---|---|---|---|
| Critical | Docker runtime no validado en engine real | Impide certificación operacional | Levantar Docker Desktop y ejecutar suite completa |
| Critical | Identity/service-discovery/config-server no integrados al Compose principal | Runtime empresarial incompleto | Incorporar servicios o registrar ADR de alcance |
| High | Actuator/logging no está normalizado en los 28 servicios | Health y observabilidad inconsistentes | Aplicar platform-config compartido |
| High | No existen pruebas automatizadas reales DB/Kafka/Keycloak | Integraciones no verificadas | Añadir Testcontainers y smoke tests |
| Medium | Scripts POSIX y PowerShell duplican comandos | Mantenimiento duplicado | Definir una interfaz operacional única |
| Low | Baseline CPU/memoria/latencia pendiente | Sin referencia de capacidad | Medir después de arranque estable |
