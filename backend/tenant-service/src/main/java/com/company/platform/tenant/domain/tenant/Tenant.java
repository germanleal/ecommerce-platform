package com.company.platform.tenant.domain.tenant;

import java.time.Instant;
import java.util.Map;

public final class Tenant {
    private final TenantId id; private final java.util.UUID organizationId; private TenantName name; private Slug slug; private final Map<String, Object> configuration;
    private TenantStatus status; private final Instant createdAt; private Instant updatedAt;
    private Tenant(TenantId id, java.util.UUID organizationId, TenantName name, Slug slug, Map<String,Object> configuration, TenantStatus status, Instant createdAt, Instant updatedAt) {
        if (organizationId == null) throw new IllegalArgumentException("organization is required");
        this.id=id; this.organizationId=organizationId; this.name=name; this.slug=slug; this.configuration=configuration == null ? Map.of() : Map.copyOf(configuration); this.status=status; this.createdAt=createdAt; this.updatedAt=updatedAt;
    }
    public static Tenant create(java.util.UUID organizationId, TenantName name, Slug slug, Map<String,Object> configuration) { var now=Instant.now(); return new Tenant(TenantId.newId(),organizationId,name,slug,configuration,TenantStatus.CREATED,now,now); }
    public static Tenant create(TenantName name, Slug slug, Map<String,Object> configuration) { return create(java.util.UUID.randomUUID(), name, slug, configuration); }
    public static Tenant rehydrate(TenantId id, java.util.UUID organizationId, TenantName name, Slug slug, Map<String,Object> configuration, TenantStatus status, Instant createdAt, Instant updatedAt) { return new Tenant(id,organizationId,name,slug,configuration,status,createdAt,updatedAt); }
    public void activate() { require(TenantStatus.CREATED, TenantStatus.SUSPENDED); status=TenantStatus.ACTIVE; touch(); }
    public void update(String name, String slug) { if (status == TenantStatus.DEACTIVATED) throw new IllegalStateException("tenant is deactivated"); this.name = new TenantName(name); this.slug = new Slug(slug); touch(); }
    public void suspend() { require(TenantStatus.ACTIVE); status=TenantStatus.SUSPENDED; touch(); }
    public void deactivate() { require(TenantStatus.CREATED, TenantStatus.ACTIVE, TenantStatus.SUSPENDED); status=TenantStatus.DEACTIVATED; touch(); }
    public TenantId id(){return id;} public java.util.UUID organizationId(){return organizationId;} public TenantName name(){return name;} public Slug slug(){return slug;} public TenantStatus status(){return status;} public Map<String,Object> configuration(){return configuration;} public Instant createdAt(){return createdAt;} public Instant updatedAt(){return updatedAt;}
    private void require(TenantStatus... allowed){for(var candidate:allowed) if(status==candidate) return; throw new IllegalStateException("invalid tenant state transition from "+status);}
    private void touch(){updatedAt=Instant.now();}
}
