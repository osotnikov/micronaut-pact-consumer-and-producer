package example.articles.service.client;

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

    public ArticlesClient(final @Client("${api.endpoint.articles.host}") RxHttpClient articlesClient) {
        this.articlesClient = articlesClient;
    }

    /*public String getArticleContent(String key) {
        String reply = null;
        reply = articlesClient.retrieve(
                GET("/articles?key=" + key), String.class).blockingFirst();
        return reply;
    }*/

    public String getArticleContent(String key) {
        String reply = null;
        Flowable<HttpResponse<ByteBuffer>> flowable = articlesClient.exchange(
                GET("/articles?key=" + key));
        HttpResponse<ByteBuffer> response = flowable.blockingFirst();
        String bodyStr = new String(response.getBody().get().toByteArray());
        return bodyStr;
    }

    public void getNoContent() {
        String reply = null;
        Flowable<HttpResponse<ByteBuffer>> flowable = articlesClient.exchange(
                GET("/bananas"));
        HttpResponse<ByteBuffer> response = flowable.blockingFirst();
        response.getStatus();
    }

    public List<String> getAllArticles() {
        return ALLOWED_KEYS.stream().map(this::getArticleContent).collect(Collectors.toList());
    }

}
