param(
    [string]$RepositoryRoot = (Resolve-Path (Join-Path $PSScriptRoot '..\..')).Path
)

$ErrorActionPreference = 'Stop'

function Ensure-Directory([string]$RelativePath) {
    $path = Join-Path $RepositoryRoot $RelativePath
    New-Item -ItemType Directory -Force -Path $path | Out-Null
    $keep = Join-Path $path '.gitkeep'
    if (-not (Test-Path $keep)) { New-Item -ItemType File -Path $keep | Out-Null }
}

function Ensure-File([string]$RelativePath, [string]$Content) {
    $path = Join-Path $RepositoryRoot $RelativePath
    $parent = Split-Path $path -Parent
    New-Item -ItemType Directory -Force -Path $parent | Out-Null
    if (-not (Test-Path $path)) { Set-Content -Path $path -Value $Content -Encoding UTF8 }
}

$readme = @"
# {0}

## Objetivo
Estructura inicial preparada para la implementación posterior.

## Responsabilidad
Pendiente de definición en los contratos de implementación.

## Estado
Bootstrap

## Dependencias
Sin dependencias técnicas en esta fase.

## Engineering Contracts relacionados
EC-005, EC-006, EC-007

## Cómo ejecutar
No ejecutable: este componente contiene únicamente estructura.

## Documentación
Consultar `docs/` y los contratos relacionados.

## Versionado
0.1.0
"@
$changelog = "# Changelog`n`n## 0.1.0 - Bootstrap`n`n- Creación de la estructura inicial.`n"
$license = "MIT License`n`nCopyright (c) 2026 Ecommerce Platform contributors.`n"

$backendServices = @('api-gateway','service-discovery','config-server','identity-service','tenant-service','marketplace-service','catalog-service','product-service','inventory-service','pricing-service','promotion-service','cart-service','checkout-service','order-service','payment-service','shipping-service','customer-service','review-service','notification-service','media-service','search-service','administration-service','audit-service','monitoring-service')
$backendDirs = @('src/main/java','src/main/resources','src/test/java','docs','tests','docker','scripts','ADR')
foreach ($service in $backendServices) {
    foreach ($dir in $backendDirs) { Ensure-Directory "backend/$service/$dir" }
    Ensure-File "backend/$service/README.md" ($readme -f $service)
    Ensure-File "backend/$service/CHANGELOG.md" $changelog
    Ensure-File "backend/$service/LICENSE" $license
    Ensure-File "backend/$service/pom.xml" "<!-- Bootstrap placeholder. Dependencies and Spring configuration are intentionally deferred. -->`n"
    Ensure-File "backend/$service/.editorconfig" "root = true`n"
    Ensure-File "backend/$service/.gitignore" "target/`n"
}

$frontends = @('marketplace','backoffice','tenant-admin','shared-ui')
$frontendDirs = @('src/app','src/pages','src/features','src/components','src/layouts','src/providers','src/services','src/hooks','src/contexts','src/store','src/routes','src/validators','src/types','src/styles','src/assets','src/docs','src/tests','public')
foreach ($app in $frontends) {
    foreach ($dir in $frontendDirs) { Ensure-Directory "frontend/$app/$dir" }
    Ensure-File "frontend/$app/README.md" ($readme -f $app)
    Ensure-File "frontend/$app/CHANGELOG.md" $changelog
    $packageJson = @"
{
  "name": "$app",
  "version": "0.1.0",
  "private": true
}
"@
    Ensure-File "frontend/$app/package.json" $packageJson
    Ensure-File "frontend/$app/tsconfig.json" "{}`n"
    Ensure-File "frontend/$app/vite.config.ts" "// Bootstrap placeholder. Vite configuration is intentionally deferred.`n"
}
foreach ($dir in @('src/components','src/tokens','src/themes','src/icons','src/providers','src/hooks','src/animations','src/stories','src/playground','docs')) { Ensure-Directory "frontend/design-system/$dir" }
Ensure-File 'frontend/design-system/README.md' ($readme -f 'design-system')
Ensure-File 'frontend/design-system/CHANGELOG.md' $changelog

foreach ($dir in @('java','typescript','contracts','schemas','clients','utilities')) { Ensure-Directory "shared/$dir"; Ensure-File "shared/$dir/README.md" ($readme -f "shared/$dir"); Ensure-File "shared/$dir/CHANGELOG.md" $changelog }
foreach ($dir in @('docker','compose','postgres','kafka','keycloak','grafana','prometheus','tempo','loki','nginx','certificates','volumes','backups','scripts')) { Ensure-Directory "infrastructure/$dir"; Ensure-File "infrastructure/$dir/README.md" ($readme -f "infrastructure/$dir") }
foreach ($dir in @('architecture','domain','development','operations','deployment','api','user-guide','diagrams','decisions')) { Ensure-Directory "docs/$dir"; Ensure-File "docs/$dir/README.md" ($readme -f "docs/$dir") }
foreach ($dir in @('bootstrap','build','development','quality','docker','database','release','utilities')) { Ensure-Directory "scripts/$dir"; Ensure-File "scripts/$dir/README.md" ($readme -f "scripts/$dir") }
foreach ($dir in @('generators','validators','migration','utilities','templates')) { Ensure-Directory "tools/$dir"; Ensure-File "tools/$dir/README.md" ($readme -f "tools/$dir") }
foreach ($dir in @('unit','integration','contracts','e2e','performance','fixtures','builders','mocks')) { Ensure-Directory "tests/$dir"; Ensure-File "tests/$dir/README.md" ($readme -f "tests/$dir") }
Ensure-Directory 'examples'
Ensure-Directory '.github'

foreach ($contract in @('EC-000','EC-001','EC-002','EC-003','EC-004','EC-005','EC-006','EC-007')) {
    Ensure-Directory "engineering/contracts/$contract/ARTIFACTS"
    Ensure-File "engineering/contracts/$contract/README.md" ($readme -f $contract)
    Ensure-File "engineering/contracts/$contract/CHANGELOG.md" $changelog
}
foreach ($dir in @('templates','standards','governance','guidelines','quality','metrics','reviews','adr','checklists','ai')) { Ensure-Directory "engineering/$dir" }

Ensure-File 'LICENSE' $license
Ensure-File 'CHANGELOG.md' $changelog
Ensure-File '.editorconfig' "root = true`n[*]`ncharset = utf-8`nend_of_line = lf`ninsert_final_newline = true`n"
Ensure-File '.gitattributes' "* text=auto eol=lf`n"
Ensure-File '.gitignore' "target/`nnode_modules/`n.env`n"
Ensure-File '.env.example' "# Environment placeholders for future infrastructure contracts.`n"
Ensure-File '.env' "# Local environment placeholder. No functional values are defined in EC-007.`n"
Ensure-File 'docker-compose.yml' "# EC-007 bootstrap placeholder. Services are intentionally deferred.`nservices: {}`n"
Ensure-File 'docker-compose.override.yml' "# Local override placeholder.`nservices: {}`n"
Ensure-File 'docker-compose.dev.yml' "# Development compose placeholder.`nservices: {}`n"

Write-Host 'EC-007 bootstrap structure ensured.'
