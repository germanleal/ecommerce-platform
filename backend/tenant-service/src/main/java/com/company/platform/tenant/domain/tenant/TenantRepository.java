package com.company.platform.tenant.domain.tenant;

import java.util.Optional;

public interface TenantRepository { Tenant save(Tenant tenant); Optional<Tenant> findById(TenantId id); Optional<Tenant> findBySlug(Slug slug); }
