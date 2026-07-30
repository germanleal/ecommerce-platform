package com.company.platform.commerce.api;
import com.company.platform.commerce.application.PricingApplicationService; import com.company.platform.commerce.domain.pricing.Money; import org.springframework.web.bind.annotation.*; import java.math.BigDecimal; import java.time.Instant; import java.util.*;
@RestController @RequestMapping("/commerce") public class CommerceController { private final PricingApplicationService service; public CommerceController(PricingApplicationService service){this.service=service;}
 public record EnableRequest(UUID marketplaceProductId,UUID storeId){} public record PriceRequest(UUID sellableProductId,BigDecimal amount,String currency,Instant validFrom,Instant validUntil){}
 @PostMapping("/products/{id}/enable") public Object enable(@PathVariable UUID id,@RequestBody EnableRequest r){return service.enable(id,r.storeId());}
 @PostMapping("/prices") public Object price(@RequestBody PriceRequest r){return service.createPrice(r.sellableProductId(),new Money(r.amount(),Currency.getInstance(r.currency())),r.validFrom(),r.validUntil());}
 @GetMapping("/products/{id}/current-price") public Object current(@PathVariable UUID id){return service.currentPrice(id).orElseThrow();}
}
