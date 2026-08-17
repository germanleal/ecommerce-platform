package com.company.platform.order.infrastructure;

import com.company.platform.order.domain.events.OrderEvent;
import com.company.platform.shared.contracts.PlatformEvent;
import org.slf4j.MDC; import org.springframework.kafka.core.KafkaTemplate; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component;
import java.util.*;

@Component public class KafkaOrderEventPublisher { private final KafkaTemplate<String,Object> kafka; public KafkaOrderEventPublisher(KafkaTemplate<String,Object> kafka){this.kafka=kafka;} public void publish(OrderEvent event){var auth=SecurityContextHolder.getContext().getAuthentication();String user=auth==null?"system":auth.getName();String correlation=Optional.ofNullable(MDC.get("correlationId")).orElseGet(()->UUID.randomUUID().toString());var payload=Map.of("eventType",event.eventType(),"aggregateType","Order","aggregateId",event.aggregateId().toString(),"data",event.payload());kafka.send("order.events",event.aggregateId().toString(),new PlatformEvent<>(event.eventId().toString(),event.tenantId().toString(),user,correlation,event.occurredAt(),"order-service",payload));}}
