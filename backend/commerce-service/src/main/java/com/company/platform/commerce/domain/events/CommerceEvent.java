package com.company.platform.commerce.domain.events;
import java.time.Instant; import java.util.UUID;
public record CommerceEvent(UUID eventId,String eventType,UUID aggregateId,UUID tenantId,Instant occurredAt,Object payload) { }
