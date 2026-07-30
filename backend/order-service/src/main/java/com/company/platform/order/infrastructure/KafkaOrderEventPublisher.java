package com.company.platform.order.infrastructure;
import org.springframework.kafka.core.KafkaTemplate;import org.springframework.stereotype.Component;import com.company.platform.order.domain.events.OrderEvent;
@Component public class KafkaOrderEventPublisher{private final KafkaTemplate<String,Object> kafka;public KafkaOrderEventPublisher(KafkaTemplate<String,Object> k){kafka=k;}public void publish(OrderEvent e){kafka.send("order.events",e.aggregateId().toString(),e);}}
