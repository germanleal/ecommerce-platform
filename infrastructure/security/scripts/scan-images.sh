#!/usr/bin/env sh
set -eu
command -v trivy >/dev/null 2>&1 || { echo 'trivy is required'; exit 2; }
echo 'Scan explicit image names from docker-compose.yml before publication.'
