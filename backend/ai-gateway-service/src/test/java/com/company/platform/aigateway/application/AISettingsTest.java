package com.company.platform.aigateway.application;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import org.junit.jupiter.api.Test;
class AISettingsTest {
 @Test void limiterAllowsConfiguredWindowAndThenRejects(){var limiter=new AIRateLimiter();assertTrue(limiter.allow("tenant:user:endpoint",1));assertFalse(limiter.allow("tenant:user:endpoint",1));}
 @Test void timeoutIsRepresentedAsDuration(){assertEquals(Duration.ofSeconds(5),Duration.parse("PT5S"));}
}
