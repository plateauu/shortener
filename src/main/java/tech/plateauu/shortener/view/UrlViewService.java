package tech.plateauu.shortener.view;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.plateauu.shortener.model.Url;
import tech.plateauu.shortener.model.UrlRepository;

import java.util.List;

@Component
class UrlViewService {

    private final UrlRepository repository;

    @Autowired
    UrlViewService(UrlRepository repository) {
        this.repository = repository;
    }

    //TODO Configure paging and sort orders
    List<Url> fetchUrl(int offset, int limit, List<UrlSort> sordOrders) {
        return repository.findAll();
    }

    int urlCount() {
        return repository.findAll().size();
    }

    UrlSort createSort(String propertyName, boolean descending) {
        return new UrlSort(propertyName, descending);
    }

    @Value
    static class UrlSort {
        private final String propertyName;
        private final boolean descending;
    }

}
