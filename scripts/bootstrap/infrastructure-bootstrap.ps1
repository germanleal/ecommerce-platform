param([string]$RepositoryRoot = (Resolve-Path (Join-Path $PSScriptRoot '..\..')).Path)
$ErrorActionPreference = 'Stop'
function Ensure-Dir([string]$relative) { $path = Join-Path $RepositoryRoot $relative; New-Item -ItemType Directory -Force $path | Out-Null; $keep = Join-Path $path '.gitkeep'; if (-not (Test-Path $keep)) { New-Item -ItemType File $keep | Out-Null } }
function Ensure-File([string]$relative, [string]$content) { $path = Join-Path $RepositoryRoot $relative; New-Item -ItemType Directory -Force (Split-Path $path -Parent) | Out-Null; if (-not (Test-Path $path)) { Set-Content -Path $path -Value $content -Encoding UTF8 } }
$readme = "# {0}`n`nEstructura de infraestructura preparada por EC-009. No contiene configuración funcional.`n`nEstado: Bootstrap`nVersión: 1.0.0`n"
$infra = @('docker','compose','postgres','kafka','keycloak','nginx','minio','mailpit','grafana','prometheus','tempo','loki','otel','network','volumes','backup','restore','healthchecks','certificates','config','logs','monitoring','scripts')
foreach ($item in $infra) { Ensure-Dir "infrastructure/$item"; Ensure-File "infrastructure/$item/README.md" ($readme -f "Infrastructure/$item") }
foreach ($item in @('docker','config','init','backup','restore','health','scripts','database/migrations/common','database/migrations/tenants','database/migrations/reference','database/migrations/seed')) { Ensure-Dir "infrastructure/postgres/$item" }
foreach ($item in @('docker','config','topics','schemas','scripts','health')) { Ensure-Dir "infrastructure/kafka/$item" }
foreach ($item in @('docker','themes','providers','imports','exports','health')) { Ensure-Dir "infrastructure/keycloak/$item" }
foreach ($item in @('config','certificates','proxy','templates','health')) { Ensure-Dir "infrastructure/nginx/$item" }
foreach ($item in @('config','buckets','policies','health')) { Ensure-Dir "infrastructure/minio/$item" }
foreach ($item in @('config','rules','targets','alerts')) { Ensure-Dir "infrastructure/prometheus/$item" }
foreach ($item in @('dashboards','datasources','plugins','provisioning')) { Ensure-Dir "infrastructure/grafana/$item" }
foreach ($item in @('config','storage')) { Ensure-Dir "infrastructure/loki/$item"; Ensure-Dir "infrastructure/tempo/$item" }
foreach ($item in @('collector','config','processors','exporters','receivers')) { Ensure-Dir "infrastructure/otel/$item" }
foreach ($item in @('development','localhost','internal')) { Ensure-Dir "infrastructure/certificates/$item" }
foreach ($item in @('postgres','keycloak','minio','scripts')) { Ensure-Dir "infrastructure/backup/$item"; Ensure-Dir "infrastructure/restore/$item" }
foreach ($item in @('postgres','kafka','keycloak','gateway','grafana','prometheus','tempo','loki','frontend')) { Ensure-Dir "infrastructure/logs/$item" }
foreach ($item in @('postgres','kafka','keycloak','nginx','grafana','prometheus','tempo','loki','minio','mailpit')) { Ensure-Dir "infrastructure/healthchecks/$item" }
foreach ($item in @('bootstrap','up','down','restart','clean','logs','backup','restore','health','update')) { Ensure-Dir "infrastructure/scripts/$item" }
foreach ($service in @('postgres-data','kafka-data','keycloak-data','grafana-data','prometheus-data','tempo-data','loki-data','minio-data','mailpit-data')) { Ensure-Dir "infrastructure/volumes/$service" }
foreach ($project in (Get-ChildItem (Join-Path $RepositoryRoot 'backend') -Directory) + (Get-ChildItem (Join-Path $RepositoryRoot 'frontend') -Directory)) {
    Ensure-File "$(if ($project.Parent.Name -eq 'backend') {'backend'} else {'frontend'})/$($project.Name)/Dockerfile" "# EC-009 placeholder. Runtime image definition is deferred.`n"
    Ensure-File "$(if ($project.Parent.Name -eq 'backend') {'backend'} else {'frontend'})/$($project.Name)/Dockerfile.dev" "# EC-009 development placeholder. Runtime definition is deferred.`n"
    Ensure-File "$(if ($project.Parent.Name -eq 'backend') {'backend'} else {'frontend'})/$($project.Name)/.dockerignore" "target`nnode_modules`n"
}
Ensure-File 'infrastructure/CHANGELOG.md' "# Infrastructure Changelog`n`n## 1.0.0`n`n- EC-009 infrastructure bootstrap structure created.`n"
Ensure-File 'infrastructure/RESTORE.md' "# Restore Strategy`n`nPrerequisites, procedure, validation and rollback are reserved for a later infrastructure contract.`n"
Ensure-File '.env.local' "# Local infrastructure placeholders. No secrets are committed.`n"
Ensure-File '.env.development' "# Development infrastructure placeholders. No secrets are committed.`n"
Write-Host 'EC-009 infrastructure structure ensured.'
