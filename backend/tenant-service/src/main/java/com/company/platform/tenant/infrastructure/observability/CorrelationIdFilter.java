package com.company.platform.tenant.infrastructure.observability;

import jakarta.servlet.FilterChain; import jakarta.servlet.ServletException; import jakarta.servlet.http.HttpServletRequest; import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC; import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException; import java.util.UUID;

public final class CorrelationIdFilter extends OncePerRequestFilter {
    public static final String HEADER = "X-Correlation-ID";
    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String correlationId = request.getHeader(HEADER); if (correlationId == null || correlationId.isBlank()) correlationId = UUID.randomUUID().toString();
        response.setHeader(HEADER, correlationId); try (var ignored = MDC.putCloseable("correlationId", correlationId)) { chain.doFilter(request, response); }
    }
}
