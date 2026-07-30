param([string]$RepositoryRoot = (Resolve-Path (Join-Path $PSScriptRoot '..\..')).Path)
$ErrorActionPreference = 'Stop'
Set-Location $RepositoryRoot
$required = @('infrastructure','docker-compose.yml','docker-compose.override.yml','docker-compose.dev.yml','infrastructure/README.md','infrastructure/CHANGELOG.md','infrastructure/RESTORE.md')
$missing = @($required | Where-Object { -not (Test-Path $_) })
$networks = @('frontend-network','backend-network','messaging-network','database-network','observability-network','identity-network','storage-network')
$volumes = @('postgres-data','kafka-data','keycloak-data','grafana-data','prometheus-data','tempo-data','loki-data','minio-data','mailpit-data')
$compose = Get-Content -Raw docker-compose.yml
$missingNetworks = @($networks | Where-Object { $compose -notmatch "(?m)^  $([regex]::Escape($_))`:" })
$missingVolumes = @($volumes | Where-Object { $compose -notmatch "(?m)^  $([regex]::Escape($_))`:" })
$images = @(Select-String -Path docker-compose*.yml -Pattern '^s*image:' | ForEach-Object Line)
$latestImages = @($images | Where-Object { $_ -match ':s*latests*$' })
$dockerfiles = @(rg --files backend frontend | Where-Object { $_ -match 'Dockerfile$' })
Write-Output 'EC-009 Infrastructure Bootstrap Audit'
Write-Output "Missing required items: $($missing.Count)"
Write-Output "Networks: $($networks.Count - $missingNetworks.Count)/$($networks.Count)"
Write-Output "Volumes: $($volumes.Count - $missingVolumes.Count)/$($volumes.Count)"
Write-Output "Dockerfiles: $($dockerfiles.Count)"
Write-Output "latest image references: $($latestImages.Count)"
if ($missing.Count -gt 0 -or $missingNetworks.Count -gt 0 -or $missingVolumes.Count -gt 0 -or $latestImages.Count -gt 0) { exit 1 }
Write-Output 'RESULT: PASS'
