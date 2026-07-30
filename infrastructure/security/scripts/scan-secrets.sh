#!/usr/bin/env sh
set -eu
command -v gitleaks >/dev/null 2>&1 || { echo 'gitleaks is required'; exit 2; }
gitleaks detect --redact --config infrastructure/security/.gitleaks.toml
