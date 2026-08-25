package com.company.platform.commerce.domain.cart;
import com.company.platform.commerce.domain.pricing.Money; import java.time.Instant; import java.util.UUID;
public record CartItem(UUID id,UUID cartId,UUID sellableProductId,int quantity,Money unitPrice,Instant createdAt,Instant updatedAt) { public CartItem {if(cartId==null||sellableProductId==null||quantity<=0||unitPrice==null)throw new IllegalArgumentException("invalid cart item");if(id==null)id=UUID.randomUUID();if(createdAt==null)createdAt=Instant.now();if(updatedAt==null)updatedAt=createdAt;} }
