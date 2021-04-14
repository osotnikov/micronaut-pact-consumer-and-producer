package example.articles.service.client;

import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
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

    public String getArticleContent(String key) {
        return articlesClient.retrieve(
                GET("/articles?key=" + key), String.class).blockingFirst();
    }

    public List<String> getAllArticles() {
        return ALLOWED_KEYS.stream().map(this::getArticleContent).collect(Collectors.toList());
    }

}
