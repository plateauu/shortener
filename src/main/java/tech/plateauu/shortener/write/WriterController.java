package tech.plateauu.shortener.write;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.plateauu.shortener.client.HttpClient;
import tech.plateauu.shortener.model.Url;
import tech.plateauu.shortener.model.UrlRepository;

/**
 * Controller responsible for add new Urls
 */
@RestController
public class WriterController {

    private final UrlRepository repository;
    private final HttpClient client;

    @Autowired
    public WriterController(UrlRepository repository, HttpClient client) {
        this.repository = repository;
        this.client = client;
    }

    //TODO Remove URL object argument list against simple string
    @PostMapping(path = "/add", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Url addUrl(@RequestBody Url url) {
        String shortUrl = client.doShort(url.getLongUrl());
        return repository.save(Url.of(shortUrl, url.getLongUrl()));
    }

    @GetMapping(path = "/check", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    Url checkUrl(@RequestParam(value = "url") String urlToExpand) {
        String longUrl = client.expand(urlToExpand);
        return Url.of(urlToExpand, longUrl);
    }

}
