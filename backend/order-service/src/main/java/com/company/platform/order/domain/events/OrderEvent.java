package com.company.platform.order.domain.events;
import java.time.Instant;import java.util.UUID;public record OrderEvent(UUID eventId,String eventType,UUID aggregateId,UUID tenantId,Instant occurredAt,Object payload){}
