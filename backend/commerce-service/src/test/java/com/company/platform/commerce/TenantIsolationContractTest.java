package com.company.platform.commerce;
import com.company.platform.commerce.domain.cart.*; import org.junit.jupiter.api.Test; import java.util.*; import static org.junit.jupiter.api.Assertions.*;
class TenantIsolationContractTest {
 @Test void aCartIsNotVisibleThroughAnotherTenant(){UUID tenantA=UUID.randomUUID(),tenantB=UUID.randomUUID(),cartId=UUID.randomUUID();Cart cart=Cart.rehydrate(cartId,tenantA,UUID.randomUUID(),UUID.randomUUID(),CartStatus.ACTIVE,null,null,List.of());Map<String,Cart> store=new HashMap<>();store.put(tenantA+":"+cartId,cart);CartRepository repository=new CartRepository(){public Cart save(Cart c){return c;} public Optional<Cart> findByTenantIdAndId(UUID t,UUID id){return Optional.ofNullable(store.get(t+":"+id));} public Optional<Cart> findByTenantIdAndCustomerId(UUID t,UUID id){return Optional.empty();}};assertTrue(repository.findByTenantIdAndId(tenantA,cartId).isPresent());assertTrue(repository.findByTenantIdAndId(tenantB,cartId).isEmpty());}
}
