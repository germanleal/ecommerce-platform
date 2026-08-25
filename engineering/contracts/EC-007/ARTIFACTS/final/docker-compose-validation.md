# Docker Compose Validation

`docker compose config --quiet`: PASS.

`docker compose build` / `docker compose up -d --build`: BLOCKED. Error observado: `failed to connect to the docker API ... dockerDesktopLinuxEngine`.

No se ejecutó `down -v` porque el daemon no estaba disponible y esa operación elimina datos locales cuando el daemon sí está activo.
