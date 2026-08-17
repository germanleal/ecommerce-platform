package com.company.platform.tenant.application.services;

import com.company.platform.tenant.domain.organization.*;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class OrganizationApplicationService {
    private final OrganizationRepository repository;
    public OrganizationApplicationService(OrganizationRepository repository) { this.repository = repository; }
    public Organization create(String name, String slug) { return repository.save(Organization.create(name, slug)); }
    public Organization get(UUID id) { return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("organization not found")); }
    public Organization update(UUID id, String name, String slug) { var o=get(id); o.update(name, slug); return repository.save(o); }
    public Organization activate(UUID id) { var o=get(id); o.activate(); return repository.save(o); }
    public Organization suspend(UUID id) { var o=get(id); o.suspend(); return repository.save(o); }
    public Organization deactivate(UUID id) { var o=get(id); o.deactivate(); return repository.save(o); }
}
