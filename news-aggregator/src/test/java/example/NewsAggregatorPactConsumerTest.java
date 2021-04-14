package example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

@MicronautTest
public class NewsAggregatorPactConsumerTest {

    @Test
    void testHello() {
        assertEquals(
                "Hello Fred!",
                "Hello Fred!");
    }
}
