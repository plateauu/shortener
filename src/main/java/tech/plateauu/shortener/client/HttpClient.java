package tech.plateauu.shortener.client;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * Http client based on spring rest template
 */
@Slf4j
public class HttpClient {

    private final static String SHORTEN_URL = "https://www.googleapis.com/urlshortener/v1/url?key=";
    private final static String EXPAND_URL = "https://www.googleapis.com/urlshortener/v1/url?shortUrl=%s&key=%s";

    private final String APIKey;
    private final RestTemplate restTemplate;

    public HttpClient(String apiKey, RestTemplate restTemplate) {
        this.APIKey = apiKey;
        this.restTemplate = restTemplate;
    }

    public String doShort(String longUrl) {
        log.info("Querying for short url: {}", longUrl);
        String url = new StringBuilder()
                .append(SHORTEN_URL)
                .append(APIKey)
                .toString();

        ResponseEntity<ShortenResponse> response = getPostForShorten(url, longUrl);

        log.info("Short url status: {}", response.getStatusCodeValue());
        String shortUrl = response.getBody().getId();
        log.info("Short url for {} is [{}]", longUrl, shortUrl);
        return shortUrl;

    }

    public String expand(String shortUrl) {
        log.info("Querying for expand url: {}", shortUrl);
        String url = String.format(EXPAND_URL, shortUrl, APIKey);
        ResponseEntity<ExpandResponse> response = getForExpandResponse(url);

        log.info("Expand url status: {}", response.getStatusCodeValue());
        ExpandResponse body = response.getBody();
        String longUrl = body.getLongUrl();

        log.info("Expanded url for {} is [{}]", shortUrl, longUrl);
        return longUrl;
    }

    private ResponseEntity<ShortenResponse> getPostForShorten(String address, String urlToShort) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ShortenBody> entity = new HttpEntity<>(ShortenBody.of(urlToShort), headers);
            return restTemplate.exchange(address, HttpMethod.POST, entity, ShortenResponse.class);
        } catch (RestClientException exception) {
            throw HttpClientConnectionException.connectionError(urlToShort, exception);
        }
    }

    private ResponseEntity<ExpandResponse> getForExpandResponse(String urlToExpand) {
        try {
            return restTemplate.getForEntity(urlToExpand, ExpandResponse.class);
        } catch (RestClientException exception) {
            throw HttpClientConnectionException.connectionError(urlToExpand, exception);
        }
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

    @Data(staticConstructor = "of")
    private static class ExpandResponse {

        private final String longUrl;
        private final String kind;
        private final String id;
        private final String status;

    }


}
