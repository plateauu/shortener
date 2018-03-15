package tech.plateauu.shortener.client;

public interface ShortenerHttpClient {

    String doShort(String longUrl);
    String expand(String shortUrl);

}
