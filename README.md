# Ecommerce Platform

Plataforma de marketplace multi-tenant para administrar empresas, catálogos y productos, vender desde una tienda pública y gestionar órdenes de compra. Incluye una consola administrativa, autenticación centralizada y métricas operacionales por tenant.

## Componentes

- **Frontend:** React + TypeScript.
- **Servicios:** Spring Boot para marketplace, órdenes, catálogo/administración, inventario, analítica e integraciones.
- **Infraestructura:** PostgreSQL, Kafka y Keycloak, todos orquestados con Docker Compose.
- **Seguridad:** OAuth 2.0/OIDC con Keycloak y roles de plataforma o tenant.

## Levantar la demo

Requisitos: Docker Desktop con Docker Compose v2.

```powershell
Copy-Item .env.example .env
docker compose --profile demo up --build -d
docker compose ps
```

La primera compilación tarda algunos minutos. El perfil `demo` carga tenants, empresas, productos, categorías, impuestos, stock e imágenes de ejemplo.

Abre:

- Marketplace: `http://localhost:3000`
- Administración: `http://localhost:3000/admin`
- Keycloak: `http://localhost:8080`

Credenciales demo de administración:

```text
usuario: platform-admin
contraseña: DevAdmin-010A!
```

## Flujo de prueba rápido

1. En **Stores**, agrega productos —incluso de distintas tiendas— al carrito.
2. En **Cart**, crea la compra; se genera una orden por comercio.
3. En **Administration → Orders**, confirma y procesa las órdenes.
4. En **Dashboard**, revisa los KPI del tenant seleccionado.

## Operación local

```powershell
# Ver estado y logs
docker compose ps
docker compose logs -f marketplace-frontend

# Reconstruir servicios modificados
docker compose build marketplace-frontend administration-service
docker compose up -d marketplace-frontend administration-service

# Detener conservando datos
docker compose down
```

Los health checks de los servicios Spring Boot están disponibles en `/actuator/health`. La configuración local se encuentra en `.env.example`; no versiones `.env` ni credenciales reales.
