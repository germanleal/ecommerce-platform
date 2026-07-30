# Tenant Service Design

`tenant-service` usa DDD y arquitectura hexagonal. El dominio expone puertos de repositorio; los adaptadores de persistencia y seguridad permanecen fuera del dominio. La Parte 2 implementa únicamente Tenant, Store, UserTenant y contexto tenant.
