package com.company.platform.order.infrastructure;

import com.company.platform.order.application.CommerceProductPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Component
public class HttpCommerceProductAdapter implements CommerceProductPort {
 private final RestClient client;
 public HttpCommerceProductAdapter(@Value("${marketplace.base-url:http://localhost:8099}") String base){client=RestClient.builder().baseUrl(base).build();}
 @SuppressWarnings("unchecked") public ProductSnapshot resolve(UUID tenantId,UUID productId){try{Map<String,Object> p=client.get().uri("/products/{id}",productId).retrieve().body(Map.class);if(p==null||!"ACTIVE".equals(String.valueOf(p.get("status"))))throw new IllegalStateException("PRODUCT_NOT_SELLABLE");UUID productTenant=UUID.fromString(String.valueOf(p.get("tenant_id"))),store=UUID.fromString(String.valueOf(p.get("store_id")));if(!tenantId.equals(productTenant))throw new IllegalStateException("PRODUCT_TENANT_MISMATCH");return new ProductSnapshot(productId,productTenant,store,String.valueOf(p.get("sku")),String.valueOf(p.get("name")),new BigDecimal(String.valueOf(p.get("final_price"))),String.valueOf(p.get("currency")));}catch(Exception e){throw new IllegalStateException(e.getMessage()==null?"MARKETPLACE_UNAVAILABLE":e.getMessage(),e);}}
}
