package tech.plateauu.shortener.client;

import lombok.extern.slf4j.Slf4j;

/**
 * Http client based on spring rest template
 */
@Slf4j
public class MockHttpClient implements ShortenerHttpClient {

    public String doShort(String longUrl) {
        return "http://fake.goo.gl.com/short";
    }

    public String expand(String shortUrl) {
        return "http://fake.long.url";
    }
}
