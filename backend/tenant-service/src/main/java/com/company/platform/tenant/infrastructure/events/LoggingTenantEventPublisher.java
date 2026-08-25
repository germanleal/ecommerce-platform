package com.company.platform.tenant.infrastructure.events;

import com.company.platform.tenant.application.services.TenantEventPublisher;
import com.company.platform.tenant.domain.events.TenantLifecycleEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingTenantEventPublisher implements TenantEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(LoggingTenantEventPublisher.class);
    @Override public void publish(TenantLifecycleEvent event) { log.info("tenant event type={} envelope={}", event.type(), event.envelope()); }
}
