package tech.plateauu.shortener.Client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
class HttpClientConfig {

    private final Environment env;

    HttpClientConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplateBuilder template = new RestTemplateBuilder();
        template.setConnectTimeout(1000);
        return template.build();
    }

    @Bean
    public HttpClient httpClient(RestTemplate restTemplate){
        return new HttpClient(env.getProperty("google.api.url.shortener.key"),  restTemplate);
    }

}