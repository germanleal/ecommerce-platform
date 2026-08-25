[CmdletBinding()]
param(
    [ValidateSet('start','stop','restart','status','logs','config','test')]
    [string]$Command = 'status',
    [string]$Service
)

$ErrorActionPreference = 'Stop'
$Root = (Resolve-Path (Join-Path $PSScriptRoot '..\..')).Path
Set-Location $Root

switch ($Command) {
    'start'   { docker compose up -d --build $(if ($Service) { $Service }) }
    'stop'    { docker compose stop $(if ($Service) { $Service }) }
    'restart' { docker compose restart $(if ($Service) { $Service }) }
    'status'  { docker compose ps }
    'logs'    { docker compose logs --tail=200 $(if ($Service) { $Service }) }
    'config'  { docker compose config --quiet; if ($LASTEXITCODE -ne 0) { throw 'Invalid Docker Compose configuration.' }; Write-Output 'Compose configuration: PASS' }
    'test'    { & (Join-Path $Root 'scripts\quality\validate-platform-bootstrap.ps1') }
}
