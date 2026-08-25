package com.company.platform.tenant.domain.store;

import com.company.platform.tenant.domain.tenant.Slug;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public final class Store {
    private final UUID id; private final UUID tenantId; private final String name; private final Slug slug; private final Map<String,Object> configuration; private StoreStatus status; private final Instant createdAt; private Instant updatedAt;
    private Store(UUID id, UUID tenantId, String name, Slug slug, Map<String,Object> configuration, StoreStatus status, Instant createdAt) { if(tenantId==null) throw new IllegalArgumentException("tenant id is required"); if(name==null||name.isBlank()) throw new IllegalArgumentException("store name is required"); this.id=id;this.tenantId=tenantId;this.name=name;this.slug=slug;this.configuration=configuration==null?Map.of():Map.copyOf(configuration);this.status=status;this.createdAt=createdAt;this.updatedAt=createdAt; }
    public static Store create(UUID tenantId,String name,Slug slug,Map<String,Object> configuration){return new Store(UUID.randomUUID(),tenantId,name,slug,configuration,StoreStatus.ACTIVE,Instant.now());}
    public static Store rehydrate(UUID id, UUID tenantId, String name, Slug slug, Map<String,Object> configuration, StoreStatus status, Instant createdAt){return new Store(id,tenantId,name,slug,configuration,status,createdAt);}
    public UUID id(){return id;} public UUID tenantId(){return tenantId;} public String name(){return name;} public Slug slug(){return slug;} public StoreStatus status(){return status;} public Map<String,Object> configuration(){return configuration;} public Instant createdAt(){return createdAt;} public Instant updatedAt(){return updatedAt;}
}
