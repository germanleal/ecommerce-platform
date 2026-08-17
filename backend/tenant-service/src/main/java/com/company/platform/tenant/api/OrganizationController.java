package com.company.platform.tenant.api;

import com.company.platform.tenant.application.services.OrganizationApplicationService;
import com.company.platform.tenant.domain.organization.Organization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationController {
    private final OrganizationApplicationService service;
    public OrganizationController(OrganizationApplicationService service) { this.service = service; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('PLATFORM_ADMIN')") public OrganizationResponse create(@RequestBody OrganizationRequest r) { return OrganizationResponse.from(service.create(r.name(), r.slug())); }
    @GetMapping("/{id}") @PreAuthorize("hasRole('PLATFORM_ADMIN')") public OrganizationResponse get(@PathVariable UUID id) { return OrganizationResponse.from(service.get(id)); }
    @PutMapping("/{id}") @PreAuthorize("hasRole('PLATFORM_ADMIN')") public OrganizationResponse update(@PathVariable UUID id, @RequestBody OrganizationRequest r) { return OrganizationResponse.from(service.update(id, r.name(), r.slug())); }
    @PostMapping("/{id}/activate") @PreAuthorize("hasRole('PLATFORM_ADMIN')") public OrganizationResponse activate(@PathVariable UUID id) { return OrganizationResponse.from(service.activate(id)); }
    @PostMapping("/{id}/suspend") @PreAuthorize("hasRole('PLATFORM_ADMIN')") public OrganizationResponse suspend(@PathVariable UUID id) { return OrganizationResponse.from(service.suspend(id)); }
    @PostMapping("/{id}/deactivate") @PreAuthorize("hasRole('PLATFORM_ADMIN')") public OrganizationResponse deactivate(@PathVariable UUID id) { return OrganizationResponse.from(service.deactivate(id)); }
    public record OrganizationRequest(String name, String slug) { }
    public record OrganizationResponse(UUID id,String name,String slug,String status,Instant createdAt,Instant updatedAt) { static OrganizationResponse from(Organization o){return new OrganizationResponse(o.id(),o.name(),o.slug(),o.status().name(),o.createdAt(),o.updatedAt());} }
}
