package com.company.platform.tenant.domain.tenant;

import java.util.Optional;
import java.util.UUID;

public interface TenantRepository { Tenant save(Tenant tenant); Optional<Tenant> findById(TenantId id); Optional<Tenant> findBySlug(Slug slug); Optional<Tenant> findByOrganizationAndSlug(UUID organizationId, Slug slug); }
