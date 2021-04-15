package example;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.MalformedURLException;
import java.net.URL;

@Provider("articles")
/** Uncomment this and comment @PactBroker instead to test locally by pasting a .json file for the contract under
 the target/pacts folder */
@PactFolder("pacts")
//@PactBroker(host = "localhost", consumers = {"ED_UI"})
public class ArticlesControllerPactProviderTest {

    // Spin up your application just like you'd do in an integration test. You can even choose to spin it up but
    // use mocks instead of certain beans e.g. for databases or message brokers...
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer.class);

    RxHttpClient articlesClient;

    @BeforeEach
    void before(PactVerificationContext context) throws MalformedURLException {
        int port = embeddedServer.getPort();
        articlesClient = RxHttpClient.create(new URL("http://localhost:" + port));
        context.setTarget(new HttpTestTarget("localhost", port, "/"));
    }

    // This will go through the pact contract file and generate tests to verify it against the running application
    // instance that's spun on line 24.
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactTestTemplate(PactVerificationContext context) {

        context.verifyInteraction();
    }

    // Anything annotated with @State is intended to prepare the running instance for tests that declare dependence on
    // these states (in given clauses) on the consumer side.
    @State("article exists for key=latest")
    public void sampleState() {
        articlesClient.exchange(HttpRequest.POST(
                "/articles",
                "{\"key\": \"latest\", \"content\": \"Perseverance Landing - February 18, 2021\"}")).blockingFirst();
    }
}
