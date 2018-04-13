package tech.plateauu.shortener.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import tech.plateauu.shortener.AppProfiles;

@Configuration

public class HttpClientConfig {

    private final Environment env;

    HttpClientConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder template = new RestTemplateBuilder();
        template.setConnectTimeout(10000);
        return template.build();
    }

    @Bean
    @Profile(AppProfiles.PROD)
    public ShortenerHttpClient httpClient(RestTemplate restTemplate) {
        return new HttpClient(env.getProperty("google.api.url.shortener.key"), restTemplate);
    }

    @Bean
    @Profile(AppProfiles.DEV)
    public ShortenerHttpClient mockhttpClient() {
        return new MockHttpClient();
    }

}
