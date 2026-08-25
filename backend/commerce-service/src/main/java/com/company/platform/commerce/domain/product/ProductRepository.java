package com.company.platform.commerce.domain.product;
import java.util.*;
public interface ProductRepository { Product save(Product product); Optional<Product> findByTenantIdAndId(UUID tenantId,UUID id); Optional<Product> findByTenantIdAndSku(UUID tenantId,String sku); List<Product> search(UUID tenantId,String query,ProductStatus status); }
