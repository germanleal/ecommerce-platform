package com.company.platform.tenant.application.services;

import com.company.platform.tenant.domain.events.TenantLifecycleEvent;

public interface TenantEventPublisher { void publish(TenantLifecycleEvent event); }
