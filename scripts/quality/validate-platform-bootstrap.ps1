[CmdletBinding()]
param([string]$RepositoryRoot = (Resolve-Path (Join-Path $PSScriptRoot '..\..')).Path)

$ErrorActionPreference = 'Stop'
Set-Location $RepositoryRoot
$required = @('backend','frontend','shared','infrastructure','engineering','docs','scripts','tests','docker-compose.yml','.env.example','configuration\platform.env.example')
$missing = @($required | Where-Object { -not (Test-Path $_) })
$compose = docker compose config --quiet 2>&1
if ($LASTEXITCODE -ne 0) { throw "Docker Compose validation failed: $compose" }
$services = @(Get-ChildItem backend -Directory | Select-Object -ExpandProperty Name)
$java = @(rg --files backend | Where-Object { $_ -match '\.java$' })
$tests = @(rg --files backend | Where-Object { $_ -match 'src[\\/]test[\\/].+\.(java|ts|tsx)$' })

Write-Output 'EC-007 Platform Bootstrap Validation'
Write-Output "Backend services: $($services.Count)"
Write-Output "Java sources: $($java.Count)"
Write-Output "Test sources: $($tests.Count)"
Write-Output "Missing required items: $($missing.Count)"
if ($missing.Count -gt 0) { $missing | ForEach-Object { Write-Output "MISSING: $_" }; exit 1 }
Write-Output 'RESULT: PASS'
