package tech.plateauu.shortener.Client;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Http client based on spring rest template
 */
@Slf4j
public class HttpClient {

    private final static String SHORTEN_URL = "https://www.googleapis.com/urlshortener/v1/url?key=";
    private final static String EXPAND_URL = "https://www.googleapis.com/urlshortener/v1/url?key=";

    private final String APIKey;
    private final RestTemplate restTemplate;

    HttpClient(String apiKey, RestTemplate restTemplate) {
        APIKey = apiKey;
        this.restTemplate = restTemplate;
    }

    public String doShort(String longUrl) {
        log.info("Querying for short url: {}", longUrl);

        String url = new StringBuilder()
                .append(SHORTEN_URL)
                .append(APIKey)
                .toString();

        ResponseEntity<ShortenResponse> response = restTemplate.postForEntity(url, ShortenBody.of(longUrl), ShortenResponse.class);

        log.info("Short url status: {}", response.getStatusCodeValue());
        String shortUrl = response.getBody().getId();

        log.info("Short url for url {} is [{}]", longUrl, shortUrl);
        return shortUrl;

    }

    @Data(staticConstructor = "of")
    private static class ShortenBody {

        private final String longUrl;

    }

    @Data(staticConstructor = "of")
    private static class ShortenResponse {

        private final String longUrl;
        private final String kind;
        private final String id;

    }


}
