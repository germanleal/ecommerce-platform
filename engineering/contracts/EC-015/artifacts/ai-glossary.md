# AI Readiness Ubiquitous Language

| Concepto | Definición | Regla |
|---|---|---|
| AI Gateway | Punto único de entrada futuro | Nunca bypass de seguridad |
| Provider | Servicio o modelo que procesa una solicitud | Se encapsula en adapter |
| Context Provider | Port que entrega contexto autorizado | API/eventos, nunca SQL |
| Tool | Capacidad invocable por un agente | Permiso, schema y auditoría |
| Prompt | Instrucción versionada | No contiene secretos |
| Memory | Estado de sesión o conocimiento persistente | Aislada por tenant |
| AI Event | Evento público consumible por IA | Versionado e idempotente |
| Guardrail | Restricción de seguridad o política | Deny by default |
| Human Approval | Confirmación para acción sensible | Obligatoria según riesgo |
| AI Audit | Registro de uso de capacidades inteligentes | Append-only y tenant-scoped |
