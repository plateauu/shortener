package tech.plateauu.shortener.read;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.plateauu.shortener.model.Url;

import java.util.List;
import java.util.Set;

@RequestMapping("/find")
@RestController
class ReaderController {

    private final UrlReader reader;

    @Autowired
    ReaderController(UrlReader reader) {
        this.reader = reader;
    }

    @GetMapping("/all")
    List<Url> shortUrl() {
        return reader.findAll();
    }

}
