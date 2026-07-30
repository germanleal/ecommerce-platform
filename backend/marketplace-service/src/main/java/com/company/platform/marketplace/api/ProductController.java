package com.company.platform.marketplace.api;

import com.company.platform.marketplace.application.MarketplaceApplicationService; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import java.util.UUID;

@RestController @RequestMapping("/products")
public class ProductController {
    private final MarketplaceApplicationService service; public ProductController(MarketplaceApplicationService service){this.service=service;}
    public record CreateProductRequest(UUID storeId,UUID catalogId,UUID categoryId,String sku,String name,String description) { }
    @PostMapping public Object create(@RequestBody CreateProductRequest r){return service.createProduct(r.storeId(),r.catalogId(),r.categoryId(),r.sku(),r.name(),r.description());}
    @GetMapping("/{id}") public Object get(@PathVariable UUID id){return service.getProduct(id);}
    @PostMapping("/{id}/publish") @ResponseStatus(HttpStatus.OK) public Object publish(@PathVariable UUID id){return service.publishProduct(id);}
}
