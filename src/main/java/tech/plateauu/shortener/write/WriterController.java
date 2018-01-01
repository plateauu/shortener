package tech.plateauu.shortener.write;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.plateauu.shortener.Client.HttpClient;
import tech.plateauu.shortener.model.Url;
import tech.plateauu.shortener.model.UrlRepository;

/**
 * Controller responsible for add new Urls
 */
@RestController
class WriterController {

    private final UrlRepository repository;
    private final HttpClient client;

    @Autowired
    public WriterController(UrlRepository repository, HttpClient client) {
        this.repository = repository;
        this.client = client;
    }

    @PostMapping(path = "/add", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    Url addUrl(@RequestBody Url url) {
        client.doShort(url.getLongUrl());
        return repository.save(url);
    }

}
