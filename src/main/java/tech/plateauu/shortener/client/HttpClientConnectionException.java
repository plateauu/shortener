package tech.plateauu.shortener.client;

import org.springframework.web.client.RestClientException;

import java.util.Arrays;

class HttpClientConnectionException extends RuntimeException {

    private static final String MESSAGE = "An exception occurred when trying to connect to %s. [%s] %n %s";

    private HttpClientConnectionException(String message) {
        super(message);
    }

    public static HttpClientConnectionException connectionError(String url, RestClientException exception) {
        return new HttpClientConnectionException(String.format(MESSAGE, url, exception.getMessage(), Arrays.toString(exception.getStackTrace())));
    }
}
