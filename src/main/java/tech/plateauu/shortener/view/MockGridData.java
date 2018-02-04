package tech.plateauu.shortener.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tech.plateauu.shortener.model.Url;
import tech.plateauu.shortener.model.UrlRepository;

@Component
class MockGridData implements CommandLineRunner {

    private final UrlRepository repository;

    @Autowired
    public MockGridData(UrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.save(Url.of("test", "longTest"));
        repository.save(Url.of("testDue", "DuelongTest"));
        repository.save(Url.of("http://some.url", "http://short"));

    }
}
