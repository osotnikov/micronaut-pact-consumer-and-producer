package example;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller("/")
@Validated
public class ArticlesController {

    private final static Set<String> ALLOWED_KEYS = Set.of("latest", "most_popular");
    private static Map<String, String> articlesRepository = new HashMap<>();

    @Post(uri = "/articles", consumes = MediaType.APPLICATION_JSON)
    public HttpResponse postArticle(@NotBlank String key, @NotBlank String content) {
        if(ALLOWED_KEYS.contains(key)){
            articlesRepository.put(key, content);
            return HttpResponse.ok();
        } else {
            return HttpResponse.badRequest();
        }
    }

    @Get(uri = "/articles", produces = MediaType.TEXT_PLAIN)
    public HttpResponse<String> getArticle(@NotBlank String key) {
        return HttpResponse.ok(articlesRepository.get(key));
    }
}
