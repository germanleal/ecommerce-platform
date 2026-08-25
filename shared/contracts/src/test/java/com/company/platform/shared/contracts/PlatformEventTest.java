package com.company.platform.shared.contracts;
import static org.junit.jupiter.api.Assertions.*;import java.time.Instant;import org.junit.jupiter.api.Test;
class PlatformEventTest{@Test void eventRequiresSecurityMetadata(){assertThrows(IllegalArgumentException.class,()->new PlatformEvent<>("e",null,"u","c",Instant.now(),"order",java.util.Map.of()));}}
