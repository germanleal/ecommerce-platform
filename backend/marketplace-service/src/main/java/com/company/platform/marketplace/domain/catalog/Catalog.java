package com.company.platform.marketplace.domain.catalog;

import java.util.UUID;

public final class Catalog {
    private final UUID id=UUID.randomUUID(); private final UUID tenantId; private final UUID storeId; private final String name; private CatalogStatus status=CatalogStatus.DRAFT;
    private Catalog(UUID tenantId,UUID storeId,String name){if(tenantId==null||storeId==null)throw new IllegalArgumentException("tenant and store required");if(name==null||name.isBlank())throw new IllegalArgumentException("catalog name required");this.tenantId=tenantId;this.storeId=storeId;this.name=name;}
    public static Catalog create(UUID tenantId,UUID storeId,String name){return new Catalog(tenantId,storeId,name);} public void activate(){status=CatalogStatus.ACTIVE;} public void deactivate(){status=CatalogStatus.INACTIVE;}
    public UUID id(){return id;} public UUID tenantId(){return tenantId;} public UUID storeId(){return storeId;} public String name(){return name;} public CatalogStatus status(){return status;}
}
