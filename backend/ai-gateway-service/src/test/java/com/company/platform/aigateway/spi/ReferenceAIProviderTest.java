package com.company.platform.aigateway.spi;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import org.junit.jupiter.api.Test;
class ReferenceAIProviderTest { @Test void mapsReferenceResponseWithoutExternalCredentials(){var response=new ReferenceAIProvider().generate(new AIRequest("hello","reference-model",UUID.randomUUID(),null,"corr"));assertEquals("reference",response.provider());assertEquals("COMPLETED",response.status());assertNotNull(response.requestId());} }
