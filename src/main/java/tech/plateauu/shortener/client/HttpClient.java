package tech.plateauu.shortener.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Http client based on spring rest template
 */
@Slf4j
public class HttpClient implements ShortenerHttpClient {

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

        ResponseEntity<ShortUrlResponse> response = getPostForShorten(url, longUrl);

        log.info("Short url status: {}", response.getStatusCodeValue());
        String shortUrl = response.getBody().getId();
        log.info("Short url for {} is [{}]", longUrl, shortUrl);
        return shortUrl;

    }

    public String expand(String shortUrl) {
        log.info("Querying for expand url: {}", shortUrl);
        String url = String.format(EXPAND_URL, shortUrl, APIKey);
        ResponseEntity<ExpandedUrlResponse> response = getExpandedUrlResponse(url);

        log.info("Expand url status: {}", response.getStatusCodeValue());
        ExpandedUrlResponse body = response.getBody();
        String longUrl = body.getLongUrl();

        log.info("Expanded url for {} is [{}]", shortUrl, longUrl);
        return longUrl;
    }

    private ResponseEntity<ShortUrlResponse> getPostForShorten(String address, String urlToShort) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ToMakeShortBody> entity = new HttpEntity<>(ToMakeShortBody.of(urlToShort), headers);
            return restTemplate.exchange(address, HttpMethod.POST, entity, ShortUrlResponse.class);
        } catch (RestClientException exception) {
            throw HttpClientConnectionException.connectionError(urlToShort, exception);
        }
    }

    private ResponseEntity<ExpandedUrlResponse> getExpandedUrlResponse(String urlToExpand) {
        try {
            return restTemplate.getForEntity(urlToExpand, ExpandedUrlResponse.class);
        } catch (RestClientException exception) {
            throw HttpClientConnectionException.connectionError(urlToExpand, exception);
        }
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    private static class ToMakeShortBody {

        private String longUrl;

    }

    @Data
    private static class ShortUrlResponse {

        private String longUrl;
        private String kind;
        private String id;

    }

    @Data
    private static class ExpandedUrlResponse {

        private String longUrl;
        private String kind;
        private String id;
        private String status;

    }


}
