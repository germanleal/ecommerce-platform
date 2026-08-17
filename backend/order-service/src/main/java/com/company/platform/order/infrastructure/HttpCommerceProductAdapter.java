package com.company.platform.order.infrastructure;

import com.company.platform.order.application.CommerceProductPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.math.BigDecimal;
import java.util.*;

@Component
public class HttpCommerceProductAdapter implements CommerceProductPort {
 private final RestClient client;
 public HttpCommerceProductAdapter(@Value("${commerce.base-url:http://localhost:8085}") String base){client=RestClient.builder().baseUrl(base).build();}
 private RestClient.RequestHeadersSpec<?> headers(RestClient.RequestHeadersSpec<?> request,UUID tenant){var a=SecurityContextHolder.getContext().getAuthentication();request.header("X-Tenant-Id",tenant.toString());if(a instanceof JwtAuthenticationToken jwt)request.header(HttpHeaders.AUTHORIZATION,"Bearer "+jwt.getToken().getTokenValue());String correlation=Optional.ofNullable(org.slf4j.MDC.get("correlationId")).orElseGet(()->UUID.randomUUID().toString());return request.header("X-Correlation-Id",correlation);}
 @SuppressWarnings("unchecked") public ProductSnapshot resolve(UUID tenantId,UUID productId){try{Map<String,Object> p=headers(client.get().uri("/api/products/{id}",productId),tenantId).retrieve().body(Map.class);Map<String,Object> price=headers(client.get().uri("/api/products/{id}/price",productId),tenantId).retrieve().body(Map.class);if(p==null||price==null||!"ACTIVE".equals(String.valueOf(p.get("status"))))throw new IllegalStateException("PRODUCT_NOT_SELLABLE");Map<String,Object> money=(Map<String,Object>)price.get("money");return new ProductSnapshot(productId,String.valueOf(p.get("sku")),String.valueOf(p.get("name")),new BigDecimal(String.valueOf(money.get("amount"))),String.valueOf(money.get("currency")));}catch(Exception e){throw new IllegalStateException(e.getMessage()==null?"COMMERCE_UNAVAILABLE":e.getMessage(),e);}}
}
