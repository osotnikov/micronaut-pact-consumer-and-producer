package example;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("articles")
/** Uncomment this and comment @PactBroker instead to test locally by pasting a .json file for the contract under
 the target/pacts folder */
@PactFolder("pacts")
//@PactBroker(host = "localhost", consumers = {"ED_UI"})
public class ArticlesControllerPactProviderTest {

    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer.class);

    @BeforeEach
    void before(PactVerificationContext context) {
        int port = embeddedServer.getPort();
        context.setTarget(new HttpTestTarget("localhost", port, "/"));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactTestTemplate(PactVerificationContext context) {

        context.verifyInteraction();
    }

    @State("article exists for key=latest")
    public void sampleState() {
        // TODO: need to make this add an entry for key=latest otherwise the pact verification will keep failing
        System.out.println("I ran!");
    }
}
