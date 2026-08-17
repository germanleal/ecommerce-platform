#!/usr/bin/env sh
set -eu
base=${PLATFORM_HEALTH_BASE_URL:-http://localhost}
for endpoint in "$base:8092/actuator/health" "$base:8093/actuator/health" "$base:8094/actuator/health" "$base:8095/actuator/health" "$base:8096/actuator/health"; do
  curl --fail --silent --show-error "$endpoint" >/dev/null
  echo "PASS $endpoint"
done
