package tech.plateauu.shortener.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import tech.plateauu.shortener.read.UrlReader;

import javax.annotation.PostConstruct;

@SpringView(name = AllShortcutsView.VIEW_NAME)
@UIScope
class AllShortcutsView extends VerticalLayout implements View {

    static final String VIEW_NAME = "allShortcuts";

    private final UrlReader reader;

    @Autowired
    AllShortcutsView(UrlReader reader) {
        this.reader = reader;
    }

    @PostConstruct
    void init(){

    }
}
