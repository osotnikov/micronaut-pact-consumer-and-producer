package example;

import io.micronaut.core.io.buffer.ByteBuffer;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;

import javax.inject.Singleton;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.micronaut.http.HttpRequest.GET;

@Singleton
public class ArticlesClient {

    private final static Set<String> ALLOWED_KEYS = Set.of("latest", "most_popular");

    private final RxHttpClient articlesClient;

    public ArticlesClient(final @Client("http://localhost:8080") RxHttpClient articlesClient) {
        this.articlesClient = articlesClient;
    }

    public String getArticleContent(String key) {
        String reply = null;
        Flowable<HttpResponse<ByteBuffer>> flowable = articlesClient.exchange(
                GET("/articles?key=" + key));
        HttpResponse<ByteBuffer> response = flowable.blockingFirst();
        String bodyStr = new String(response.getBody().get().toByteArray());
        return bodyStr;
    }

}
