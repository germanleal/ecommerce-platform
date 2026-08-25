[CmdletBinding()]
param(
    [string]$KeycloakBaseUrl = $env:IAM_KEYCLOAK_URL,
    [string]$Realm = 'platform',
    [string]$ClientId = $(if ([string]::IsNullOrWhiteSpace($env:IAM_CLIENT_ID)) { 'iam-smoke' } else { $env:IAM_CLIENT_ID }),
    [string]$ClientSecret = $(if ([string]::IsNullOrWhiteSpace($env:IAM_CLIENT_SECRET)) { 'dev-iam-smoke-secret' } else { $env:IAM_CLIENT_SECRET }),
    [string]$Username = $(if ([string]::IsNullOrWhiteSpace($env:IAM_USERNAME)) { 'platform-admin' } else { $env:IAM_USERNAME }),
    [string]$Password = $(if ([string]::IsNullOrWhiteSpace($env:IAM_PASSWORD)) { 'DevAdmin-010A!' } else { $env:IAM_PASSWORD }),
    [string]$RefreshToken = $env:IAM_REFRESH_TOKEN,
    [string]$AccessToken = $env:IAM_ACCESS_TOKEN
)
$ErrorActionPreference = 'Stop'
if (-not [string]::IsNullOrWhiteSpace($env:IAM_REALM)) { $Realm = $env:IAM_REALM }
if ([string]::IsNullOrWhiteSpace($KeycloakBaseUrl)) { $KeycloakBaseUrl = 'http://localhost:8080' }
$issuer = "$KeycloakBaseUrl/realms/$Realm"
$discovery = Invoke-RestMethod "$issuer/.well-known/openid-configuration"
if ($discovery.issuer -ne $issuer) { throw "Unexpected issuer: $($discovery.issuer)" }
Write-Output 'OIDC discovery: PASS'

$body = @{ grant_type='client_credentials'; client_id=$ClientId; client_secret=$ClientSecret }
$serviceToken = Invoke-RestMethod -Method Post -Uri $discovery.token_endpoint -ContentType 'application/x-www-form-urlencoded' -Body $body
if ([string]::IsNullOrWhiteSpace($serviceToken.access_token)) { throw 'Client Credentials returned no access token' }
Write-Output "Client Credentials ($ClientId): PASS"

$loginBody = @{ grant_type='password'; client_id=$ClientId; client_secret=$ClientSecret; username=$Username; password=$Password; scope='openid' }
$login = Invoke-RestMethod -Method Post -Uri $discovery.token_endpoint -ContentType 'application/x-www-form-urlencoded' -Body $loginBody
if ([string]::IsNullOrWhiteSpace($login.access_token) -or [string]::IsNullOrWhiteSpace($login.refresh_token)) { throw 'Login returned no access/refresh token' }
$AccessToken = $login.access_token
$RefreshToken = $login.refresh_token
Write-Output "Login ($Username): PASS"

$body = @{ grant_type='refresh_token'; client_id=$ClientId; client_secret=$ClientSecret; refresh_token=$RefreshToken }
$refreshed = Invoke-RestMethod -Method Post -Uri $discovery.token_endpoint -ContentType 'application/x-www-form-urlencoded' -Body $body
if ([string]::IsNullOrWhiteSpace($refreshed.access_token)) { throw 'Refresh Token returned no access token' }
Write-Output 'Refresh Token: PASS'

try { $protected = Invoke-WebRequest -UseBasicParsing -Uri 'http://localhost:8097/api/v1/identity/me' -TimeoutSec 5 } catch { $protected = $_.Exception.Response }
if ($protected.StatusCode -ne 401) { throw "Unauthenticated identity endpoint returned $($protected.StatusCode), expected 401" }
Write-Output 'Unauthenticated API rejection: PASS'
