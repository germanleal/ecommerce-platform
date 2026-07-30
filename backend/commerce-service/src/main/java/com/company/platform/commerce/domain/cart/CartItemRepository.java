package com.company.platform.commerce.domain.cart;
import java.util.*;
public interface CartItemRepository { CartItem save(UUID tenantId, CartItem item); List<CartItem> findAllByTenantIdAndCartId(UUID tenantId, UUID cartId); Optional<CartItem> findByTenantIdAndCartIdAndId(UUID tenantId, UUID cartId, UUID id); void deleteByTenantIdAndCartIdAndId(UUID tenantId, UUID cartId, UUID id); }
