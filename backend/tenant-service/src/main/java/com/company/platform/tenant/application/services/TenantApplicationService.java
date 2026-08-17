package com.company.platform.tenant.application.services;

import com.company.platform.tenant.domain.tenant.*; import com.company.platform.tenant.domain.organization.*; import com.company.platform.tenant.domain.usertenant.*; import com.company.platform.tenant.domain.events.TenantLifecycleEvent; import org.springframework.stereotype.Service; import java.time.Instant; import java.util.Map; import java.util.UUID;

@Service
public class TenantApplicationService {
    private final TenantRepository repository; private final OrganizationRepository organizations; private final UserTenantRepository members; private final TenantEventPublisher events;
    public TenantApplicationService(TenantRepository repository, OrganizationRepository organizations, UserTenantRepository members, TenantEventPublisher events){this.repository=repository;this.organizations=organizations;this.members=members;this.events=events;}
    public Tenant create(UUID organizationId,String name,String slug, Map<String,Object> configuration, UUID ownerId){
        var organization=organizations.findById(organizationId).orElseThrow(()->new IllegalArgumentException("organization not found"));
        if (organization.status() != OrganizationStatus.ACTIVE) throw new IllegalStateException("organization is not active");
        var tenant=repository.save(Tenant.create(organizationId,new TenantName(name),new Slug(slug),configuration));
        tenant.activate(); tenant=repository.save(tenant);
        if(ownerId != null) members.save(new UserTenant(null,ownerId,tenant.id().value(),"TENANT_OWNER","ACTIVE",Instant.now()));
        events.publish(new TenantLifecycleEvent("TenantCreated",tenant.id().value(),ownerId,Instant.now(),UUID.randomUUID().toString())); return tenant;
    }
    public Tenant create(String name,String slug, Map<String,Object> configuration){throw new IllegalArgumentException("organizationId and ownerId are required");}
    public Tenant create(String name,String slug){return create(name, slug, Map.of());}
    public Tenant get(TenantId id){return repository.findById(id).orElseThrow(()->new TenantNotFoundException(id.value().toString()));}
    public Tenant update(TenantId id, String name, String slug){ var t=get(id); t.update(name, slug); return repository.save(t); }
    public java.util.List<UserTenant> members(UUID tenantId){ get(new TenantId(tenantId)); return members.findByTenantId(tenantId); }
    public UserTenant addMember(UUID tenantId, UUID userId, String role){ get(new TenantId(tenantId)); if(members.findByUserIdAndTenantId(userId,tenantId).filter(m -> !"REMOVED".equals(m.status())).isPresent()) throw new IllegalArgumentException("membership already exists"); return members.save(new UserTenant(null,userId,tenantId,role,"ACTIVE",Instant.now())); }
    public UserTenant changeMemberRole(UUID tenantId, UUID userId, String role){ var m=member(tenantId,userId); return members.save(new UserTenant(m.id(),m.userId(),m.tenantId(),role,m.status(),m.createdAt())); }
    public UserTenant changeMemberStatus(UUID tenantId, UUID userId, String status){ var m=member(tenantId,userId); return members.save(new UserTenant(m.id(),m.userId(),m.tenantId(),m.role(),status,m.createdAt())); }
    public void removeMember(UUID tenantId, UUID userId){ member(tenantId,userId); members.remove(userId,tenantId); }
    private UserTenant member(UUID tenantId, UUID userId){ return members.findByUserIdAndTenantId(userId,tenantId).orElseThrow(()->new IllegalArgumentException("membership not found")); }
    public Tenant activate(TenantId id){var t=get(id);t.activate();var saved=repository.save(t);events.publish(new TenantLifecycleEvent("TenantActivated",id.value(),null,Instant.now(),UUID.randomUUID().toString()));return saved;}
    public Tenant suspend(TenantId id){var t=get(id);t.suspend();var saved=repository.save(t);events.publish(new TenantLifecycleEvent("TenantSuspended",id.value(),null,Instant.now(),UUID.randomUUID().toString()));return saved;}
    public Tenant deactivate(TenantId id){var t=get(id);t.deactivate();var saved=repository.save(t);events.publish(new TenantLifecycleEvent("TenantDeactivated",id.value(),null,Instant.now(),UUID.randomUUID().toString()));return saved;}
}
