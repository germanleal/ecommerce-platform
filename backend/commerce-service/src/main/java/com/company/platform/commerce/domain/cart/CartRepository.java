package com.company.platform.commerce.domain.cart;
import java.util.*;
public interface CartRepository { Cart save(Cart cart); Optional<Cart> findByTenantIdAndId(UUID tenantId,UUID id); Optional<Cart> findByTenantIdAndCustomerId(UUID tenantId,UUID customerId); }
