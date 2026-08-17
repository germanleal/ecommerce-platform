package com.company.platform.payment.infrastructure;

import com.company.platform.payment.application.PaymentApplicationService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.math.BigDecimal; import java.util.Currency; import java.util.UUID;

@Component public class OrderConfirmedConsumer {
 private final PaymentApplicationService payments; public OrderConfirmedConsumer(PaymentApplicationService payments){this.payments=payments;}
 @KafkaListener(topics="order.events",groupId="payment-service",containerFactory="paymentKafkaListenerContainerFactory")
 public void consume(JsonNode event){if(!"ORDER_CONFIRMED".equals(event.path("payload").path("eventType").asText()))return;JsonNode data=event.path("payload").path("data");UUID tenant=UUID.fromString(event.path("tenantId").asText());UUID order=UUID.fromString(data.path("orderId").asText());UUID customer=UUID.fromString(data.path("customerId").asText());BigDecimal amount=data.path("total").decimalValue();Currency currency=Currency.getInstance(data.path("currency").asText());payments.processOrderConfirmed(tenant,order,customer,amount,currency,event.path("eventId").asText());}
}
