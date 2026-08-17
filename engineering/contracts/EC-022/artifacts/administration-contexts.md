# Administration Models

Tenant contiene identidad, estado, plan y configuración base. UserProfile referencia identidad externa y membresías; no almacena passwords. Role agrupa permisos; Permission es una capacidad atómica. Configuration tiene clave, valor versionado, scope y effectiveAt. FeatureFlag tiene clave, estado, reglas de targeting y rollout. Notification representa una comunicación administrativa. AuditRecord es inmutable y contiene actor, tenant, acción, recurso, resultado y correlationId.

Las relaciones se validan por tenant y las modificaciones relevantes producen eventos públicos de administración.
