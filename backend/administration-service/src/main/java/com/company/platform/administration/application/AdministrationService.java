package com.company.platform.administration.application;

import com.company.platform.administration.infrastructure.TenantContextProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import org.slf4j.MDC;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdministrationService {
    private final JdbcTemplate jdbc;
    private final TenantContextProvider tenantContext;
    private final ObjectMapper mapper;
    public AdministrationService(JdbcTemplate jdbc, TenantContextProvider tenantContext, ObjectMapper mapper) {
        this.jdbc = jdbc; this.tenantContext = tenantContext; this.mapper = mapper;
    }
    private boolean platform(Authentication a) { return a != null && a.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(x -> x.equals("PLATFORM_ADMIN") || x.equals("ROLE_PLATFORM_ADMIN")); }
    private UUID actor(Authentication a) { try { return a == null ? null : UUID.fromString(a.getName()); } catch (RuntimeException e) { return null; } }
    private UUID tenant(Authentication a, UUID requested, boolean write, String... roles) {
        if (platform(a)) return requested;
        if (a == null || !a.isAuthenticated() || requested == null) throw new SecurityException("tenant administration requires an authenticated tenant");
        UUID effective = tenantContext.currentTenantId();
        if (!requested.equals(effective)) throw new SecurityException("cross-tenant administration denied");
        UUID user = actor(a);
        if (user == null) throw new SecurityException("authenticated subject must be a UUID");
        String role = jdbc.query("SELECT role FROM public.user_tenants WHERE user_id=? AND tenant_id=? AND status='ACTIVE'", rs -> rs.next() ? rs.getString(1) : null, user, effective);
        if (role == null || (write && Arrays.stream(roles).noneMatch(role::equals))) throw new SecurityException("insufficient tenant administration role");
        return effective;
    }
    private static Timestamp ts(Instant i) { return Timestamp.from(i); }
    private static String json(ObjectMapper mapper, Object value) { try { return mapper.writeValueAsString(value == null ? Map.of() : value); } catch (Exception e) { throw new IllegalArgumentException("invalid configuration"); } }
    private void audit(Authentication a, UUID tenant, String action, String type, UUID resource, String result) {
        jdbc.update("INSERT INTO administration_audit(id,tenant_id,actor_id,action,resource_type,resource_id,result,correlation_id,created_at) VALUES(?,?,?,?,?,?,?,?,?)",
                UUID.randomUUID(), tenant, actor(a), action, type, resource, result, correlation(), ts(Instant.now()));
    }
    private UUID correlation() { try { return UUID.fromString(Optional.ofNullable(MDC.get("correlationId")).orElseGet(() -> UUID.randomUUID().toString())); } catch (RuntimeException e) { return UUID.randomUUID(); } }

    public List<Map<String,Object>> organizations(Authentication a, int page, int size) {
        if (!platform(a)) throw new SecurityException("platform administration required");
        int offset = Math.max(page, 0) * Math.min(Math.max(size, 1), 100);
        return jdbc.queryForList("SELECT id,name,slug,status,created_at,updated_at FROM public.organizations ORDER BY name LIMIT ? OFFSET ?", Math.min(Math.max(size, 1), 100), offset);
    }
    public Map<String,Object> organization(Authentication a, UUID id) { if (!platform(a)) throw new SecurityException("platform administration required"); return one("SELECT id,name,slug,status,created_at,updated_at FROM public.organizations WHERE id=?", id, "organization"); }
    @Transactional public Map<String,Object> createOrganization(Authentication a, String name, String slug) {
        if (!platform(a)) throw new SecurityException("platform administration required");
        UUID id=UUID.randomUUID(); Instant now=Instant.now(); jdbc.update("INSERT INTO public.organizations(id,name,slug,status,created_at,updated_at) VALUES(?,?,?,?,?,?)", id,name,slug,"ACTIVE",ts(now),ts(now)); audit(a,null,"ORGANIZATION_CREATED","Organization",id,"SUCCESS"); return organization(a,id);
    }
    @Transactional public Map<String,Object> updateOrganization(Authentication a, UUID id, String name, String slug) {
        if (!platform(a)) throw new SecurityException("platform administration required"); jdbc.update("UPDATE public.organizations SET name=?,slug=?,updated_at=? WHERE id=?",name,slug,ts(Instant.now()),id); audit(a,null,"ORGANIZATION_UPDATED","Organization",id,"SUCCESS"); return organization(a,id);
    }
    @Transactional public Map<String,Object> changeOrganizationStatus(Authentication a, UUID id, String status) {
        if (!platform(a)) throw new SecurityException("platform administration required");
        if (!Set.of("ACTIVE","SUSPENDED","DEACTIVATED").contains(status)) throw new IllegalArgumentException("invalid organization status");
        String current=(String)organization(a,id).get("status");
        if ("DEACTIVATED".equals(current) && !"DEACTIVATED".equals(status)) throw new IllegalStateException("deactivated organization is terminal");
        if ("ACTIVE".equals(status) && !"SUSPENDED".equals(current)) throw new IllegalStateException("invalid organization lifecycle transition");
        if ("SUSPENDED".equals(status) && !"ACTIVE".equals(current)) throw new IllegalStateException("invalid organization lifecycle transition");
        jdbc.update("UPDATE public.organizations SET status=?,updated_at=? WHERE id=?",status,ts(Instant.now()),id); audit(a,null,"ORGANIZATION_"+status,"Organization",id,"SUCCESS"); return organization(a,id);
    }

    public List<Map<String,Object>> tenants(Authentication a, int page, int size) {
        int limit=Math.min(Math.max(size,1),100), offset=Math.max(page,0)*limit;
        if (platform(a)) return jdbc.queryForList("SELECT id,organization_id,name,slug,status,configuration,created_at,updated_at FROM public.tenants ORDER BY name LIMIT ? OFFSET ?",limit,offset);
        UUID id=tenant(a, tenantContext.currentTenantId(), false); return List.of(one("SELECT id,organization_id,name,slug,status,configuration,created_at,updated_at FROM public.tenants WHERE id=?",id,"tenant"));
    }
    public Map<String,Object> tenant(Authentication a, UUID id) { tenant(a,id,false); return one("SELECT id,organization_id,name,slug,status,configuration,created_at,updated_at FROM public.tenants WHERE id=?",id,"tenant"); }
    @Transactional public Map<String,Object> createTenant(Authentication a, UUID organizationId, String name, String slug, Map<String,Object> config, UUID ownerId) {
        if (!platform(a)) throw new SecurityException("platform administration required");
        if (jdbc.queryForObject("SELECT COUNT(*) FROM public.organizations WHERE id=? AND status='ACTIVE'",Integer.class,organizationId)==0) throw new NoSuchElementException("organization not found");
        UUID id=UUID.randomUUID(); Instant now=Instant.now(); jdbc.update("INSERT INTO public.tenants(id,organization_id,name,slug,status,configuration,created_at,updated_at) VALUES(?,?,?,?,?,?,?,?)",id,organizationId,name,slug,"ACTIVE",json(mapper,config),ts(now),ts(now));
        if(ownerId!=null) jdbc.update("INSERT INTO public.user_tenants(id,user_id,tenant_id,role,status,created_at,updated_at) VALUES(?,?,?,?,?,?,?)",UUID.randomUUID(),ownerId,id,"TENANT_OWNER","ACTIVE",ts(now),ts(now));
        audit(a,id,"TENANT_CREATED","Tenant",id,"SUCCESS"); return tenant(a,id);
    }
    @Transactional public Map<String,Object> updateTenant(Authentication a, UUID id, String name, String slug, Map<String,Object> config) {
        tenant(a,id,true,"TENANT_OWNER","TENANT_ADMIN"); jdbc.update("UPDATE public.tenants SET name=?,slug=?,configuration=?,updated_at=? WHERE id=?",name,slug,json(mapper,config),ts(Instant.now()),id); audit(a,id,"TENANT_UPDATED","Tenant",id,"SUCCESS"); return tenant(a,id);
    }
    @Transactional public Map<String,Object> changeTenantStatus(Authentication a, UUID id, String status) {
        tenant(a,id,true,"TENANT_OWNER","TENANT_ADMIN"); if(!Set.of("ACTIVE","SUSPENDED","DEACTIVATED").contains(status)) throw new IllegalArgumentException("invalid tenant status");
        String current=(String)tenant(a,id).get("status"); if("DEACTIVATED".equals(current) && !"DEACTIVATED".equals(status)) throw new IllegalStateException("deactivated tenant is terminal");
        if("ACTIVE".equals(status) && !Set.of("CREATED","SUSPENDED").contains(current)) throw new IllegalStateException("invalid tenant lifecycle transition");
        if("SUSPENDED".equals(status) && !"ACTIVE".equals(current)) throw new IllegalStateException("invalid tenant lifecycle transition");
        jdbc.update("UPDATE public.tenants SET status=?,updated_at=? WHERE id=?",status,ts(Instant.now()),id); audit(a,id,"TENANT_"+status,"Tenant",id,"SUCCESS"); return tenant(a,id);
    }

    public List<Map<String,Object>> members(Authentication a, UUID tenantId) { tenant(a,tenantId,false); return jdbc.queryForList("SELECT id,user_id,tenant_id,role,status,created_at,updated_at FROM public.user_tenants WHERE tenant_id=? ORDER BY created_at",tenantId); }
    @Transactional public Map<String,Object> addMember(Authentication a, UUID tenantId, UUID userId, String role) { tenant(a,tenantId,true,"TENANT_OWNER","TENANT_ADMIN"); validateRole(role); UUID id=UUID.randomUUID(); Instant now=Instant.now(); jdbc.update("INSERT INTO public.user_tenants(id,user_id,tenant_id,role,status,created_at,updated_at) VALUES(?,?,?,?,?,?,?)",id,userId,tenantId,role,"ACTIVE",ts(now),ts(now)); audit(a,tenantId,"MEMBERSHIP_CREATED","TenantMember",id,"SUCCESS"); return member(tenantId,userId); }
    @Transactional public Map<String,Object> changeRole(Authentication a, UUID tenantId, UUID userId, String role) { tenant(a,tenantId,true,"TENANT_OWNER","TENANT_ADMIN"); validateRole(role); Map<String,Object> old=member(tenantId,userId); if("TENANT_OWNER".equals(old.get("role")) && !"TENANT_OWNER".equals(role) && owners(tenantId)<=1) throw new IllegalStateException("cannot remove the last tenant owner"); jdbc.update("UPDATE public.user_tenants SET role=?,updated_at=now() WHERE tenant_id=? AND user_id=?",role,tenantId,userId); audit(a,tenantId,"ROLE_CHANGED","TenantMember",(UUID)old.get("id"),"SUCCESS"); return member(tenantId,userId); }
    @Transactional public Map<String,Object> changeMemberStatus(Authentication a, UUID tenantId, UUID userId, String status) { tenant(a,tenantId,true,"TENANT_OWNER","TENANT_ADMIN"); Map<String,Object> old=member(tenantId,userId); if("TENANT_OWNER".equals(old.get("role")) && !"ACTIVE".equals(status) && owners(tenantId)<=1) throw new IllegalStateException("cannot suspend the last tenant owner"); if(!Set.of("ACTIVE","SUSPENDED","REMOVED").contains(status)) throw new IllegalArgumentException("invalid membership status"); jdbc.update("UPDATE public.user_tenants SET status=?,updated_at=now() WHERE tenant_id=? AND user_id=?",status,tenantId,userId); audit(a,tenantId,"MEMBERSHIP_"+status,"TenantMember",(UUID)old.get("id"),"SUCCESS"); return member(tenantId,userId); }
    public void removeMember(Authentication a, UUID tenantId, UUID userId) { changeMemberStatus(a,tenantId,userId,"REMOVED"); }
    private int owners(UUID tenantId){return jdbc.queryForObject("SELECT COUNT(*) FROM public.user_tenants WHERE tenant_id=? AND role='TENANT_OWNER' AND status='ACTIVE'",Integer.class,tenantId);}
    private void validateRole(String role){if(!Set.of("TENANT_OWNER","TENANT_ADMIN","TENANT_OPERATOR","TENANT_VIEWER").contains(role)) throw new IllegalArgumentException("invalid tenant role");}
    private Map<String,Object> member(UUID tenantId,UUID userId){return one("SELECT id,user_id,tenant_id,role,status,created_at,updated_at FROM public.user_tenants WHERE tenant_id=? AND user_id=?",new Object[]{tenantId,userId},"membership");}
    public List<Map<String,Object>> audit(Authentication a, UUID tenantId, int page, int size){UUID scope=platform(a)?tenantId:tenant(a,tenantId,false); int limit=Math.min(Math.max(size,1),100),offset=Math.max(page,0)*limit; if(scope==null)return jdbc.queryForList("SELECT * FROM administration_audit ORDER BY created_at DESC LIMIT ? OFFSET ?",limit,offset); return jdbc.queryForList("SELECT * FROM administration_audit WHERE tenant_id=? ORDER BY created_at DESC LIMIT ? OFFSET ?",scope,limit,offset);}
    private Map<String,Object> one(String sql,Object arg,String label){return jdbc.queryForList(sql,arg instanceof Object[]?(Object[])arg:new Object[]{arg}).stream().findFirst().orElseThrow(()->new NoSuchElementException(label+" not found"));}
    public Map<String,Object> createLegacy(String type,Map<String,Object> body,Authentication a){UUID scope=tenantContext.currentTenantId(); tenant(a,scope,true,"TENANT_OWNER","TENANT_ADMIN"); UUID id=UUID.randomUUID(); audit(a,scope,"CREATE_"+type.toUpperCase(Locale.ROOT),type,id,"SUCCESS"); return Map.of("id",id,"tenantId",scope,"resource",type,"data",body);}
    public List<Map<String,Object>> listLegacy(String type,Authentication a){UUID scope=tenant(a,tenantContext.currentTenantId(),false); return jdbc.queryForList("SELECT * FROM administration_"+type.replace('-','_')+" WHERE tenant_id=?",scope);}
    public Map<String,Object> updateFlag(UUID id,Map<String,Object> body,Authentication a){UUID scope=tenant(a,tenantContext.currentTenantId(),true,"TENANT_OWNER","TENANT_ADMIN"); jdbc.update("UPDATE administration_feature_flags SET enabled=?,version=version+1,updated_at=? WHERE tenant_id=? AND id=?",body.getOrDefault("enabled",false),ts(Instant.now()),scope,id); audit(a,scope,"FEATURE_FLAG_UPDATE","FeatureFlag",id,"SUCCESS"); return Map.of("id",id,"updated",true);}
}
