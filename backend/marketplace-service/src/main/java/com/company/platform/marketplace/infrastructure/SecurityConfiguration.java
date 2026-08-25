package com.company.platform.marketplace.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.UUID;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
public class SecurityConfiguration {
    @Bean SecurityFilterChain security(HttpSecurity http) throws Exception { return http.csrf(c -> c.disable()).cors(c -> {}).authorizeHttpRequests(a -> a.requestMatchers("/actuator/**").permitAll().requestMatchers(HttpMethod.GET, "/stores/**", "/products/**").permitAll().anyRequest().authenticated()).oauth2ResourceServer(o -> o.jwt(Customizer.withDefaults())).build(); }
    @Bean CorsConfigurationSource cors() { var c = new CorsConfiguration(); c.setAllowedOrigins(java.util.List.of("http://localhost:3000", "http://localhost:5173")); c.setAllowedMethods(java.util.List.of("GET", "POST", "OPTIONS")); c.setAllowedHeaders(java.util.List.of("Authorization", "Content-Type", "X-Correlation-Id")); var s = new UrlBasedCorsConfigurationSource(); s.registerCorsConfiguration("/**", c); return s; }
    @Bean TenantContextProvider tenantContextProvider() { return () -> { var auth = SecurityContextHolder.getContext().getAuthentication(); if (auth != null && auth.getPrincipal() instanceof Jwt jwt) { String value = jwt.getClaimAsString("tenant_id"); if (value == null) value = jwt.getClaimAsString("tenantId"); if (value != null) return UUID.fromString(value); } throw new IllegalStateException("tenant context is required for this operation"); }; }
}
