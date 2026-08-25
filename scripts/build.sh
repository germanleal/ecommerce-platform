#!/usr/bin/env sh
set -eu
root=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
for service in inventory-service analytics-service integration-service administration-service ai-gateway-service; do
  (cd "$root/backend/$service" && mvn -q "-Dproject.build.directory=$PWD/build-output" -DskipTests package)
done
