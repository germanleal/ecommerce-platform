package com.company.platform.shared.contracts;
import static org.junit.jupiter.api.Assertions.*;import java.util.List;import org.junit.jupiter.api.Test;
class IdentityContextTest{@Test void contextCollectionsAreImmutable(){var c=new IdentityContext("u","user",List.of("USER"),List.of("ORDER_READ"),"tenant","client","corr");assertThrows(UnsupportedOperationException.class,()->c.roles().add("ADMIN"));}}
