package com.company.platform.shared.security;

import java.util.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

/** Single RBAC conversion policy for realm roles and granular permissions. */
public final class PlatformJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtAuthenticationConverter delegate = new JwtAuthenticationConverter();
    public PlatformJwtAuthenticationConverter() { delegate.setJwtGrantedAuthoritiesConverter(this::authorities); }
    @Override public AbstractAuthenticationToken convert(Jwt source) { return delegate.convert(source); }
    private Collection<GrantedAuthority> authorities(Jwt jwt) {
        var result = new ArrayList<GrantedAuthority>();
        for (String claim : List.of("roles", "permissions")) { var values = jwt.getClaimAsStringList(claim); if (values != null) values.forEach(v -> result.add(new SimpleGrantedAuthority(v))); }
        var realm = jwt.getClaim("realm_access"); if (realm instanceof Map<?, ?> map && map.get("roles") instanceof Collection<?> roles) roles.forEach(role -> result.add(new SimpleGrantedAuthority("ROLE_" + role)));
        return result;
    }
}
