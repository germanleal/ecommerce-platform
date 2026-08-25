package com.company.platform.identity.api;

import com.company.platform.identity.application.UserContext;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/api/v1/identity")
public class IdentityController {
    private final RestClient keycloak;
    private final String clientId;
    private final String clientSecret;
    private final String issuer;

    public IdentityController(RestClient.Builder builder,
                              @Value("${identity.keycloak.token-url}") String tokenUrl,
                              @Value("${identity.keycloak.client-id:platform-backend}") String clientId,
                              @Value("${identity.keycloak.client-secret:}") String clientSecret,
                              @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuer) {
        this.keycloak = builder.baseUrl(tokenUrl).build(); this.clientId = clientId; this.clientSecret = clientSecret; this.issuer = issuer;
    }

    @GetMapping("/issuer") public Map<String, String> issuer() { return Map.of("issuer", issuer); }

    @GetMapping("/me")
    public UserContext me(@AuthenticationPrincipal Jwt jwt) {
        var roles = jwt.getClaimAsStringList("roles");
        if (roles == null) roles = jwt.getClaimAsStringList("realm_access.roles");
        return new UserContext(jwt.getSubject(), jwt.getClaimAsString("preferred_username"), tenant(jwt), jwt.getClaimAsString("azp"), roles);
    }

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<?, ?> token(@RequestParam("grant_type") String grantType,
                           @RequestParam(required = false) String code,
                           @RequestParam(required = false, name = "refresh_token") String refreshToken,
                           @RequestParam(required = false, name = "redirect_uri") String redirectUri) {
        var form = new org.springframework.util.LinkedMultiValueMap<String, String>();
        form.add("grant_type", grantType); form.add("client_id", clientId);
        if (!clientSecret.isBlank()) form.add("client_secret", clientSecret);
        if (code != null) form.add("code", code); if (refreshToken != null) form.add("refresh_token", refreshToken); if (redirectUri != null) form.add("redirect_uri", redirectUri);
        return keycloak.post().contentType(MediaType.APPLICATION_FORM_URLENCODED).body(form).retrieve().body(Map.class);
    }

    private String tenant(Jwt jwt) { return jwt.getClaimAsString("tenant_id") != null ? jwt.getClaimAsString("tenant_id") : jwt.getClaimAsString("tenantId"); }
}
