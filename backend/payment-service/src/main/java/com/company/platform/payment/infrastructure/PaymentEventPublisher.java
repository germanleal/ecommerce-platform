package com.company.platform.payment.infrastructure;

import com.company.platform.shared.contracts.PlatformEvent;
import org.slf4j.MDC; import org.springframework.kafka.core.KafkaTemplate; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component;
import java.time.Instant; import java.util.*;

@Component public class PaymentEventPublisher { private final KafkaTemplate<String,Object> kafka; public PaymentEventPublisher(KafkaTemplate<String,Object> kafka){this.kafka=kafka;} public void publish(String type,UUID tenantId,UUID aggregateId,Object payload){var auth=SecurityContextHolder.getContext().getAuthentication();String user=auth==null?"system":auth.getName();String correlation=Optional.ofNullable(MDC.get("correlationId")).orElseGet(()->UUID.randomUUID().toString());var body=Map.of("eventType",type,"aggregateType","Payment","aggregateId",aggregateId.toString(),"data",payload);kafka.send("payment.events",aggregateId.toString(),new PlatformEvent<>(UUID.randomUUID().toString(),tenantId.toString(),user,correlation,Instant.now(),"payment-service",body));}}
