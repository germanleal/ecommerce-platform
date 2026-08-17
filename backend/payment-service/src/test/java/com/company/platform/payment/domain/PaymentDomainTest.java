package com.company.platform.payment.domain;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal; import java.util.Currency; import java.util.UUID;
import org.junit.jupiter.api.Test;

class PaymentDomainTest {
    private Payment payment(){return Payment.create(UUID.randomUUID(),UUID.randomUUID(),null,"PAY-1",Currency.getInstance("USD"),new BigDecimal("10.00"),PaymentMethod.CREDIT_CARD);}
    @Test void lifecycleAllowsAuthorizationAndCapture(){Payment p=payment();p.pending();p.authorize();p.capture();assertEquals(PaymentStatus.CAPTURED,p.status());}
    @Test void lifecycleRejectsCaptureFromCreated(){assertThrows(IllegalStateException.class,()->payment().capture());}
    @Test void refundCannotExceedPaymentAmount(){assertThrows(IllegalArgumentException.class,()->Refund.create(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),"R-1",BigDecimal.ZERO,Currency.getInstance("USD"),"duplicate"));}
}
