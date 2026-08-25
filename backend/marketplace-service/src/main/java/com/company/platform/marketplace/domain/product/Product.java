package com.company.platform.marketplace.domain.product;

import java.util.UUID;

public final class Product {
    private final UUID id;
    private final UUID tenantId, storeId, catalogId, categoryId;
    private final SKU sku;
    private final ProductName name;
    private final String description;
    private ProductStatus status;
    private Product(UUID id, UUID tenantId, UUID storeId, UUID catalogId, UUID categoryId, SKU sku, ProductName name, String description, ProductStatus status) {
        if (tenantId == null || storeId == null || catalogId == null) throw new IllegalArgumentException("product ownership required");
        this.id = id == null ? UUID.randomUUID() : id; this.tenantId = tenantId; this.storeId = storeId; this.catalogId = catalogId; this.categoryId = categoryId; this.sku = sku; this.name = name; this.description = description; this.status = status == null ? ProductStatus.DRAFT : status;
    }
    public static Product create(UUID tenantId, UUID storeId, UUID catalogId, UUID categoryId, SKU sku, ProductName name, String description) { return new Product(null, tenantId, storeId, catalogId, categoryId, sku, name, description, ProductStatus.DRAFT); }
    public static Product rehydrate(UUID id, UUID tenantId, UUID storeId, UUID catalogId, UUID categoryId, SKU sku, ProductName name, String description, ProductStatus status) { return new Product(id, tenantId, storeId, catalogId, categoryId, sku, name, description, status); }
    public void publish() { if (status != ProductStatus.DRAFT) throw new IllegalStateException("only draft product can publish"); status = ProductStatus.ACTIVE; }
    public void archive() { status = ProductStatus.ARCHIVED; }
    public UUID id(){return id;} public UUID tenantId(){return tenantId;} public UUID storeId(){return storeId;} public UUID catalogId(){return catalogId;} public UUID categoryId(){return categoryId;} public SKU sku(){return sku;} public ProductName name(){return name;} public String description(){return description;} public ProductStatus status(){return status;}
}
