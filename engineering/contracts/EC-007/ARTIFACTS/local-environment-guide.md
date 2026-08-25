# EC-007 — Local environment guide

1. Copy `.env.example` to a local environment file and replace development placeholders.
2. Validate configuration with `docker compose config --quiet`.
3. Start with `./scripts/start.sh` or `./scripts/development/platform.ps1 start`.
4. Check with `./scripts/status.sh` or the PowerShell equivalent.
5. Stop with `./scripts/stop.sh`. `reset.sh` removes named volumes and is destructive to local data.

The Keycloak realm is available at `http://localhost:8080/realms/platform` after import. Local credentials come only from environment variables.
