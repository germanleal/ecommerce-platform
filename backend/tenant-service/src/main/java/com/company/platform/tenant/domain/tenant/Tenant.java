package com.company.platform.tenant.domain.tenant;

import java.time.Instant;
import java.util.Map;

public final class Tenant {
    private final TenantId id; private final TenantName name; private final Slug slug; private final Map<String, Object> configuration;
    private TenantStatus status; private final Instant createdAt; private Instant updatedAt;
    private Tenant(TenantId id, TenantName name, Slug slug, Map<String,Object> configuration, TenantStatus status, Instant createdAt, Instant updatedAt) {
        this.id=id; this.name=name; this.slug=slug; this.configuration=configuration == null ? Map.of() : Map.copyOf(configuration); this.status=status; this.createdAt=createdAt; this.updatedAt=updatedAt;
    }
    public static Tenant create(TenantName name, Slug slug, Map<String,Object> configuration) { var now=Instant.now(); return new Tenant(TenantId.newId(),name,slug,configuration,TenantStatus.PENDING,now,now); }
    public static Tenant rehydrate(TenantId id, TenantName name, Slug slug, Map<String,Object> configuration, TenantStatus status, Instant createdAt, Instant updatedAt) { return new Tenant(id,name,slug,configuration,status,createdAt,updatedAt); }
    public void activate() { ensureNotDeleted(); status=TenantStatus.ACTIVE; touch(); }
    public void suspend() { ensureNotDeleted(); status=TenantStatus.SUSPENDED; touch(); }
    public void deactivate() { ensureNotDeleted(); status=TenantStatus.INACTIVE; touch(); }
    public TenantId id(){return id;} public TenantName name(){return name;} public Slug slug(){return slug;} public TenantStatus status(){return status;} public Map<String,Object> configuration(){return configuration;} public Instant createdAt(){return createdAt;} public Instant updatedAt(){return updatedAt;}
    private void ensureNotDeleted(){if(status==TenantStatus.DELETED) throw new IllegalStateException("deleted tenant cannot change state");} private void touch(){updatedAt=Instant.now();}
}
