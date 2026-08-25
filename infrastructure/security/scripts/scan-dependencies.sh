#!/usr/bin/env sh
set -eu
if [ -f backend/tenant-service/pom.xml ]; then (cd backend/tenant-service && mvn test); fi
if [ -f frontend/marketplace/package.json ]; then (cd frontend/marketplace && npm audit --audit-level=high); fi
