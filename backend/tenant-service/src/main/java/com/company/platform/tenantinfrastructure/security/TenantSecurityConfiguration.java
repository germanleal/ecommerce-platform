package com.company.platform.tenant.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import com.company.platform.shared.security.PlatformJwtAuthenticationConverter;
import com.company.platform.tenant.domain.tenant.TenantRepository;
import com.company.platform.tenant.domain.usertenant.UserTenantRepository;

@Configuration
@EnableMethodSecurity
public class TenantSecurityConfiguration {
    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http, TenantRepository tenants, UserTenantRepository memberships) throws Exception {
        return http.csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(a -> a.requestMatchers("/actuator/health", "/actuator/info").permitAll().anyRequest().authenticated())
            .oauth2ResourceServer(o -> o.jwt(jwt -> jwt.jwtAuthenticationConverter(new PlatformJwtAuthenticationConverter())))
            .addFilterAfter(new TenantJwtTenantResolver(tenants, memberships), BearerTokenAuthenticationFilter.class)
            .build();
    }
}
