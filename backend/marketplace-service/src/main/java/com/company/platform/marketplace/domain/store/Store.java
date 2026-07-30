package com.company.platform.marketplace.domain.store;

import java.util.UUID;

public final class Store {
    private final UUID id; private final UUID tenantId; private final String name; private final String slug; private StoreStatus status;
    private Store(UUID id,UUID tenantId,String name,String slug,StoreStatus status){if(tenantId==null)throw new IllegalArgumentException("tenantId required");if(name==null||name.isBlank())throw new IllegalArgumentException("name required");this.id=id;this.tenantId=tenantId;this.name=name;this.slug=slug;this.status=status;}
    public static Store create(UUID tenantId,String name,String slug){return new Store(UUID.randomUUID(),tenantId,name,slug,StoreStatus.CREATED);}
    public void configure(){if(status!=StoreStatus.CREATED)throw new IllegalStateException("store cannot be configured");status=StoreStatus.CONFIGURED;}
    public void activate(){if(status!=StoreStatus.CONFIGURED)throw new IllegalStateException("store must be configured");status=StoreStatus.ACTIVE;}
    public void suspend(){if(status==StoreStatus.CLOSED)throw new IllegalStateException("closed store");status=StoreStatus.SUSPENDED;}
    public UUID id(){return id;} public UUID tenantId(){return tenantId;} public String name(){return name;} public String slug(){return slug;} public StoreStatus status(){return status;}
}
