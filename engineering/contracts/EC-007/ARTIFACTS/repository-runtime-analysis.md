# EC-007 — Repository runtime analysis

## Evidence

- Backend directories: 28.
- Frontend projects and shared libraries exist.
- Root Compose files: `docker-compose.yml`, `.dev.yml`, `.override.yml`.
- Bootstrap and validation scripts already existed.
- `configuration/` was absent and is now created.
- `services/` and `libs/` are not used; runtime code lives under `backend/` and `shared/`.

## Gaps for Parte 2

- Root Compose currently starts only a subset of backend services.
- No central config server runtime is wired into Compose.
- Keycloak realm import and service discovery are not fully automated.
- Health/readiness exposure is not uniform across every service.
- No single cross-platform command existed; `scripts/development/platform.ps1` is now provided for Windows.
