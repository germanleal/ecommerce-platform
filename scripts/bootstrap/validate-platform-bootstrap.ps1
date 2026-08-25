param(
    [string]$RepositoryRoot = (Resolve-Path (Join-Path $PSScriptRoot '..\..')).Path
)

$ErrorActionPreference = 'Stop'
Set-Location $RepositoryRoot

$services = @('api-gateway','service-discovery','config-server','identity-service','tenant-service','marketplace-service','catalog-service','product-service','inventory-service','pricing-service','promotion-service','cart-service','checkout-service','order-service','payment-service','shipping-service','customer-service','review-service','notification-service','media-service','search-service','administration-service','audit-service','monitoring-service')
$frontends = @('marketplace','backoffice','tenant-admin','shared-ui','design-system')
$requiredRoot = @('backend','frontend','shared','infrastructure','engineering','docs','scripts','tools','tests','examples','.github','README.md','CHANGELOG.md','LICENSE','.gitignore','.gitattributes','.editorconfig','docker-compose.yml','docker-compose.override.yml','docker-compose.dev.yml','.env.example','.env')
$missing = [System.Collections.Generic.List[string]]::new()

foreach ($path in $requiredRoot) { if (-not (Test-Path $path)) { $missing.Add($path) } }
foreach ($service in $services) {
    foreach ($path in @("backend/$service/README.md","backend/$service/CHANGELOG.md","backend/$service/pom.xml","backend/$service/src/main/java","backend/$service/src/main/resources","backend/$service/src/test/java")) { if (-not (Test-Path $path)) { $missing.Add($path) } }
}
foreach ($app in $frontends) { if (-not (Test-Path "frontend/$app/README.md")) { $missing.Add("frontend/$app/README.md") } }
foreach ($contract in 0..7) { $name = 'EC-{0:D3}' -f $contract; if (-not (Test-Path "engineering/contracts/$name")) { $missing.Add("engineering/contracts/$name") } }

$java = @(rg --files backend | Where-Object { $_ -match '\.java$' })
$react = @(rg --files frontend | Where-Object { $_ -match '\.(tsx|jsx)$' })
$apiMarkers = @(rg -n '(@RestController|@RequestMapping|openapi|swagger)' backend 2>$null)
$duplicateServices = @($services | Group-Object | Where-Object Count -gt 1)

Write-Output "EC-007 Platform Bootstrap Audit"
Write-Output "Version: v1.0.0"
Write-Output "Backend services: $($services.Count)"
Write-Output "Frontend projects: $($frontends.Count)"
Write-Output "Missing required items: $($missing.Count)"
Write-Output "Java source files: $($java.Count)"
Write-Output "React source files: $($react.Count)"
Write-Output "API markers: $($apiMarkers.Count)"
Write-Output "Duplicate service names: $($duplicateServices.Count)"

if ($missing.Count -gt 0 -or $java.Count -gt 0 -or $react.Count -gt 0 -or $apiMarkers.Count -gt 0 -or $duplicateServices.Count -gt 0) {
    if ($missing.Count -gt 0) { Write-Output 'Missing:'; $missing }
    exit 1
}

Write-Output 'RESULT: PASS'
