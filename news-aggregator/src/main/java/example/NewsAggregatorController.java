package example;

import example.articles.service.client.ArticlesClient;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;

import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller("/news-aggregator")
@Validated
public class NewsAggregatorController {

    @Inject
    protected ArticlesClient customerClient;

    @Get(uri = "/all-articles", produces = MediaType.TEXT_PLAIN)
    public HttpResponse<String> getAllArticles() {

        return HttpResponse.ok(customerClient.getAllArticles().stream()
                .reduce("", (str1, str2) -> str1 + "\n" +str2));
    }
}
