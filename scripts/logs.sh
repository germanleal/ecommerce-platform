#!/usr/bin/env sh
set -eu
docker compose logs --tail=200 "$@"
