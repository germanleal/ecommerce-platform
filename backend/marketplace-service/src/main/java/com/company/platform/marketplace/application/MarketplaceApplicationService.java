package com.company.platform.marketplace.application;

import com.company.platform.marketplace.domain.product.*; import com.company.platform.marketplace.infrastructure.TenantContextProvider; import org.springframework.stereotype.Service; import java.util.*;

@Service
public class MarketplaceApplicationService {
    private final ProductRepository products; private final TenantContextProvider tenantContext;
    public MarketplaceApplicationService(ProductRepository products,TenantContextProvider tenantContext){this.products=products;this.tenantContext=tenantContext;}
    public Product createProduct(UUID storeId,UUID catalogId,UUID categoryId,String sku,String name,String description){UUID tenantId=tenantContext.currentTenantId();return products.save(Product.create(tenantId,storeId,catalogId,categoryId,new SKU(sku),new ProductName(name),description));}
    public Product getProduct(UUID id){return products.findByTenantIdAndId(tenantContext.currentTenantId(),id).orElseThrow(()->new NoSuchElementException("product not found"));}
    public Product publishProduct(UUID id){var product=getProduct(id);product.publish();return products.save(product);}
}
