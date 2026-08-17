package com.company.platform.tenant.api;

import com.company.platform.tenant.application.services.TenantApplicationService;
import com.company.platform.tenant.domain.tenant.Tenant;
import com.company.platform.tenant.domain.tenant.TenantId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import com.company.platform.tenant.infrastructure.security.TenantAccessValidator;

import java.util.Map;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantController {
    private final TenantApplicationService service; private final TenantAccessValidator access;
    public TenantController(TenantApplicationService service, TenantAccessValidator access) { this.service = service; this.access = access; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public TenantResponse create(@RequestBody CreateTenantRequest request) {
        var tenant = service.create(request.organizationId(), request.name(), request.slug(), request.configuration(), request.ownerId());
        return TenantResponse.from(tenant);
    }

    @GetMapping("/{tenantId}")
    public TenantResponse get(@PathVariable UUID tenantId, Authentication authentication) { access.validate(authentication, tenantId, null); return TenantResponse.from(service.get(new TenantId(tenantId))); }
    @PutMapping("/{tenantId}")
    public TenantResponse update(@PathVariable UUID tenantId, @RequestBody UpdateTenantRequest request, Authentication authentication) { access.validateRole(authentication, tenantId, "TENANT_OWNER", "TENANT_ADMIN"); return TenantResponse.from(service.update(new TenantId(tenantId), request.name(), request.slug())); }

    @GetMapping("/{tenantId}/members")
    public List<MemberResponse> members(@PathVariable UUID tenantId, Authentication authentication) { access.validate(authentication, tenantId, null); return service.members(tenantId).stream().map(m -> new MemberResponse(m.id(), m.userId(), m.tenantId(), m.role(), m.status(), m.createdAt())).toList(); }
    @PostMapping("/{tenantId}/members") public MemberResponse addMember(@PathVariable UUID tenantId, @RequestBody MemberRequest request, Authentication authentication) { access.validateRole(authentication, tenantId, "TENANT_OWNER", "TENANT_ADMIN"); return MemberResponse.from(service.addMember(tenantId, request.userId(), request.role())); }
    @PatchMapping("/{tenantId}/members/{userId}/role") public MemberResponse changeRole(@PathVariable UUID tenantId, @PathVariable UUID userId, @RequestBody RoleRequest request, Authentication authentication) { access.validateRole(authentication, tenantId, "TENANT_OWNER", "TENANT_ADMIN"); return MemberResponse.from(service.changeMemberRole(tenantId, userId, request.role())); }
    @PatchMapping("/{tenantId}/members/{userId}/status") public MemberResponse changeStatus(@PathVariable UUID tenantId, @PathVariable UUID userId, @RequestBody StatusRequest request, Authentication authentication) { access.validateRole(authentication, tenantId, "TENANT_OWNER", "TENANT_ADMIN"); return MemberResponse.from(service.changeMemberStatus(tenantId, userId, request.status())); }
    @DeleteMapping("/{tenantId}/members/{userId}") @ResponseStatus(HttpStatus.NO_CONTENT) public void removeMember(@PathVariable UUID tenantId, @PathVariable UUID userId, Authentication authentication) { access.validateRole(authentication, tenantId, "TENANT_OWNER", "TENANT_ADMIN"); service.removeMember(tenantId, userId); }

    @PostMapping("/{tenantId}/activate")
    public TenantResponse activate(@PathVariable UUID tenantId, Authentication authentication) { access.validateRole(authentication, tenantId, "TENANT_OWNER", "TENANT_ADMIN"); return TenantResponse.from(service.activate(new TenantId(tenantId))); }

    @PostMapping("/{tenantId}/suspend")
    public TenantResponse suspend(@PathVariable UUID tenantId, Authentication authentication) { access.validateRole(authentication, tenantId, "TENANT_OWNER", "TENANT_ADMIN"); return TenantResponse.from(service.suspend(new TenantId(tenantId))); }

    @PostMapping("/{tenantId}/deactivate")
    public TenantResponse deactivate(@PathVariable UUID tenantId, Authentication authentication) { access.validateRole(authentication, tenantId, "TENANT_OWNER", "TENANT_ADMIN"); return TenantResponse.from(service.deactivate(new TenantId(tenantId))); }

    public record CreateTenantRequest(UUID organizationId, String name, String slug, Map<String, Object> configuration, UUID ownerId) { }
    public record UpdateTenantRequest(String name, String slug) { }
    public record MemberRequest(UUID userId, String role) { }
    public record RoleRequest(String role) { }
    public record StatusRequest(String status) { }
    public record TenantResponse(UUID id, UUID organizationId, String name, String slug, String status, Map<String,Object> configuration) {
        static TenantResponse from(Tenant t) { return new TenantResponse(t.id().value(), t.organizationId(), t.name().value(), t.slug().value(), t.status().name(), t.configuration()); }
    }
    public record MemberResponse(UUID id, UUID userId, UUID tenantId, String role, String status, java.time.Instant createdAt) { static MemberResponse from(com.company.platform.tenant.domain.usertenant.UserTenant m){return new MemberResponse(m.id(),m.userId(),m.tenantId(),m.role(),m.status(),m.createdAt());} }
}
