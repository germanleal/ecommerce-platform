[CmdletBinding()]
param(
    [string]$KeycloakBaseUrl = $(if ([string]::IsNullOrWhiteSpace($env:IAM_KEYCLOAK_URL)) { 'http://localhost:8080' } else { $env:IAM_KEYCLOAK_URL }),
    [string]$Realm = 'platform',
    [string]$AdminUser = $(if ([string]::IsNullOrWhiteSpace($env:KEYCLOAK_ADMIN)) { 'admin' } else { $env:KEYCLOAK_ADMIN }),
    [string]$AdminPassword = $(if ([string]::IsNullOrWhiteSpace($env:KEYCLOAK_ADMIN_PASSWORD)) { 'change-me' } else { $env:KEYCLOAK_ADMIN_PASSWORD })
)
$ErrorActionPreference = 'Stop'
$token = Invoke-RestMethod -Method Post -Uri "$KeycloakBaseUrl/realms/master/protocol/openid-connect/token" -ContentType 'application/x-www-form-urlencoded' -Body @{ grant_type='password'; client_id='admin-cli'; username=$AdminUser; password=$AdminPassword }
$headers = @{ Authorization = "Bearer $($token.access_token)" }
$base = "$KeycloakBaseUrl/admin/realms/$Realm"

$clients = @(
    @{ id='marketplace-service'; secret='dev-marketplace-service-secret' }, @{ id='commerce-service'; secret='dev-commerce-service-secret' },
    @{ id='orders-service'; secret='dev-orders-service-secret' }, @{ id='payments-service'; secret='dev-payments-service-secret' },
    @{ id='inventory-service'; secret='dev-inventory-service-secret' }, @{ id='analytics-service'; secret='dev-analytics-service-secret' },
    @{ id='administration-service'; secret='dev-administration-service-secret' }
)
foreach ($client in $clients) {
    $existing = @(Invoke-RestMethod -Headers $headers -Uri "$base/clients?clientId=$($client.id)")
    $body = @{ clientId=$client.id; enabled=$true; publicClient=$false; secret=$client.secret; serviceAccountsEnabled=$true; standardFlowEnabled=$false; directAccessGrantsEnabled=$false; protocol='openid-connect' } | ConvertTo-Json
    if ($existing.Count -eq 0) { Invoke-RestMethod -Method Post -Headers $headers -ContentType 'application/json' -Uri "$base/clients" -Body $body }
}

$users = @(
    @{ name='platform-admin'; password='DevAdmin-010A!'; tenant='00000000-0000-0000-0000-000000000001' },
    @{ name='tenant-admin'; password='DevTenantAdmin-010A!'; tenant='00000000-0000-0000-0000-000000000001' },
    @{ name='tenant-manager'; password='DevTenantManager-010A!'; tenant='00000000-0000-0000-0000-000000000001' },
    @{ name='tenant-user'; password='DevTenantUser-010A!'; tenant='00000000-0000-0000-0000-000000000001' },
    @{ name='tenant-b-user'; password='DevTenantBUser-010A!'; tenant='00000000-0000-0000-0000-000000000002' }
)
foreach ($user in $users) {
    $existing = @(Invoke-RestMethod -Headers $headers -Uri "$base/users?username=$($user.name)")
    $body = @{ username=$user.name; enabled=$true; email="$($user.name)@local.test"; emailVerified=$true; firstName='Development'; lastName=$user.name; requiredActions=@(); attributes=@{ tenant_id=@($user.tenant) } } | ConvertTo-Json -Depth 8
    if ($existing.Count -eq 0) { Invoke-RestMethod -Method Post -Headers $headers -ContentType 'application/json' -Uri "$base/users" -Body $body; $existing = @(Invoke-RestMethod -Headers $headers -Uri "$base/users?username=$($user.name)") }
    Invoke-RestMethod -Method Put -Headers $headers -ContentType 'application/json' -Uri "$base/users/$($existing[0].id)" -Body $body
    $credential = @{ type='password'; temporary=$false; value=$user.password } | ConvertTo-Json
    Invoke-RestMethod -Method Put -Headers $headers -ContentType 'application/json' -Uri "$base/users/$($existing[0].id)/reset-password" -Body $credential
}
Write-Output "Keycloak provisioning: PASS ($($clients.Count) clients, $($users.Count) users)"
