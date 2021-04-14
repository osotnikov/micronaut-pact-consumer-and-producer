package example.articles.service.client;

import io.micronaut.core.type.Argument;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;

import javax.inject.Singleton;

import static io.micronaut.http.HttpRequest.GET;

@Singleton
public class ArticlesClient {

    private final RxHttpClient articlesClient;

    public ArticlesClient(final @Client("${api.endpoint.articles.host}") RxHttpClient articlesClient) {
        this.articlesClient = articlesClient;
    }

    public String getArticleContent(String key) {
        return articlesClient.retrieve(
                GET("/articles?key=" + key), String.class).blockingFirst();
    }

}
