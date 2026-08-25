package com.company.platform.administration.api;

import com.company.platform.administration.application.AdministrationService;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/admin", "/admin"})
public class AdministrationController {
    private final AdministrationService service;
    public AdministrationController(AdministrationService service) { this.service = service; }
    public record OrganizationRequest(String name,String slug) {}
    public record TenantRequest(UUID organizationId,String name,String slug,Map<String,Object> configuration,UUID ownerId) {}
    public record UpdateTenantRequest(String name,String slug,Map<String,Object> configuration) {}
    public record StatusRequest(String status) {}
    public record MemberRequest(UUID userId,String role) {}

    @GetMapping("/organizations") public List<Map<String,Object>> organizations(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="50") int size,Authentication a){return service.organizations(a,page,size);}
    @GetMapping("/organizations/{id}") public Map<String,Object> organization(@PathVariable UUID id,Authentication a){return service.organization(a,id);}
    @PostMapping("/organizations") @ResponseStatus(HttpStatus.CREATED) public Map<String,Object> createOrganization(@RequestBody OrganizationRequest r,Authentication a){return service.createOrganization(a,r.name(),r.slug());}
    @PutMapping("/organizations/{id}") public Map<String,Object> updateOrganization(@PathVariable UUID id,@RequestBody OrganizationRequest r,Authentication a){return service.updateOrganization(a,id,r.name(),r.slug());}
    @PatchMapping("/organizations/{id}/status") public Map<String,Object> organizationStatus(@PathVariable UUID id,@RequestBody StatusRequest r,Authentication a){return service.changeOrganizationStatus(a,id,r.status());}
    @GetMapping("/tenants") public List<Map<String,Object>> tenants(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="50") int size,Authentication a){return service.tenants(a,page,size);}
    @GetMapping("/tenants/{id}") public Map<String,Object> tenant(@PathVariable UUID id,Authentication a){return service.tenant(a,id);}
    @PostMapping("/tenants") @ResponseStatus(HttpStatus.CREATED) public Map<String,Object> createTenant(@RequestBody TenantRequest r,Authentication a){return service.createTenant(a,r.organizationId(),r.name(),r.slug(),r.configuration(),r.ownerId());}
    @PutMapping("/tenants/{id}") public Map<String,Object> updateTenant(@PathVariable UUID id,@RequestBody UpdateTenantRequest r,Authentication a){return service.updateTenant(a,id,r.name(),r.slug(),r.configuration());}
    @PatchMapping("/tenants/{id}/status") public Map<String,Object> tenantStatus(@PathVariable UUID id,@RequestBody StatusRequest r,Authentication a){return service.changeTenantStatus(a,id,r.status());}
    @GetMapping("/tenants/{id}/members") public List<Map<String,Object>> members(@PathVariable UUID id,Authentication a){return service.members(a,id);}
    @PostMapping("/tenants/{id}/members") @ResponseStatus(HttpStatus.CREATED) public Map<String,Object> addMember(@PathVariable UUID id,@RequestBody MemberRequest r,Authentication a){return service.addMember(a,id,r.userId(),r.role());}
    @PutMapping("/tenants/{id}/members/{userId}") public Map<String,Object> changeRole(@PathVariable UUID id,@PathVariable UUID userId,@RequestBody MemberRequest r,Authentication a){return service.changeRole(a,id,userId,r.role());}
    @PatchMapping("/tenants/{id}/members/{userId}/status") public Map<String,Object> memberStatus(@PathVariable UUID id,@PathVariable UUID userId,@RequestBody StatusRequest r,Authentication a){return service.changeMemberStatus(a,id,userId,r.status());}
    @DeleteMapping("/tenants/{id}/members/{userId}") @ResponseStatus(HttpStatus.NO_CONTENT) public void removeMember(@PathVariable UUID id,@PathVariable UUID userId,Authentication a){service.removeMember(a,id,userId);}
    @GetMapping("/audit") public List<Map<String,Object>> audit(@RequestParam(required=false) UUID tenantId,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="50") int size,Authentication a){return service.audit(a,tenantId,page,size);}

    @PutMapping("/feature-flags/{id}") public Map<String,Object> flag(@PathVariable UUID id,@RequestBody Map<String,Object> body,Authentication a){return service.updateFlag(id,body,a);}
    @ExceptionHandler(NoSuchElementException.class) @ResponseStatus(HttpStatus.NOT_FOUND) public Map<String,String> notFound(NoSuchElementException e){return Map.of("error","NOT_FOUND","message",e.getMessage());}
    @ExceptionHandler(SecurityException.class) @ResponseStatus(HttpStatus.FORBIDDEN) public Map<String,String> forbidden(SecurityException e){return Map.of("error","FORBIDDEN","message",e.getMessage());}
    @ExceptionHandler({IllegalArgumentException.class,IllegalStateException.class}) @ResponseStatus(HttpStatus.BAD_REQUEST) public Map<String,String> invalid(RuntimeException e){return Map.of("error","INVALID_REQUEST","message",e.getMessage());}
}
