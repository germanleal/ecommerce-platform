# OAuth2/OIDC flow

- Browser: Authorization Code + PKCE through `web-client`.
- Service-to-service: Client Credentials using a confidential Keycloak client.
- Renewal: Refresh Token handled by Keycloak; `identity-service /api/v1/identity/token` delegates code/refresh exchange without storing tokens.
- APIs: Spring Security Resource Server validates Bearer JWT against the configured issuer.
