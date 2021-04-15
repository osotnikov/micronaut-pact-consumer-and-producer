package example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import example.articles.service.client.ArticlesClient;
import io.micronaut.test.annotation.MicronautTest;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;

import javax.inject.Inject;

@MicronautTest
@ExtendWith(PactConsumerTestExt.class)
public class ArticlesClientPactConsumerTest {

    @Inject
    ArticlesClient articlesClient;

    private final static String EXPECTED_ARTICLE_CONTENT = "Perseverance Landing - February 18, 2021";

    @Pact(provider = "articles", consumer = "news-aggregator")
    public RequestResponsePact pact(PactDslWithProvider builder) {
        // stubbing
        return builder
                .given("article exists for key=latest") // This state will have to be prepared by the provider
                // before verifying this contract(stub)
                    .uponReceiving("test get article with key=latest")
                    .method("GET")
                    .path("/articles")
                    .matchQuery("key", "latest")
                    .willRespondWith()
                    .status(200)
                    .body(EXPECTED_ARTICLE_CONTENT)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = "articles", hostInterface = "localhost", port = "8080")
    public void testFoo1() {
        // testing
        assertEquals(EXPECTED_ARTICLE_CONTENT, articlesClient.getArticleContent("latest"));
    }
}
