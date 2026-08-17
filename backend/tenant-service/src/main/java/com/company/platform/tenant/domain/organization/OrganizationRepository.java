package com.company.platform.tenant.domain.organization;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository {
    Organization save(Organization organization);
    Optional<Organization> findById(UUID id);
    Optional<Organization> findBySlug(String slug);
}
