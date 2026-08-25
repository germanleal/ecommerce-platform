package com.company.platform.identity.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain security(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/actuator/health", "/actuator/info", "/api/v1/identity/issuer").permitAll().anyRequest().authenticated())
                .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> {}))
                .build();
    }
}
