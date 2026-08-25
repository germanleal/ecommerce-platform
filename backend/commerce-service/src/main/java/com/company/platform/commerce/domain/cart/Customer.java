package com.company.platform.commerce.domain.cart;
import java.time.Instant; import java.util.UUID;
public record Customer(UUID id,UUID tenantId,UUID identityId,Instant createdAt) { public Customer {if(tenantId==null||identityId==null)throw new IllegalArgumentException("customer identity required");if(id==null)id=UUID.randomUUID();if(createdAt==null)createdAt=Instant.now();} }
