package tech.plateauu.shortener.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.TextRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import tech.plateauu.shortener.model.Url;
import tech.plateauu.shortener.read.UrlReader;

import javax.annotation.PostConstruct;
import java.util.List;

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

        addComponent(new Label("All shortcuts:"));
        final VerticalLayout layout = new VerticalLayout();

        final Grid<Url> grid = new Grid<>();
        final List<Url> items = reader.findAll();
        grid.setItems(items);

        grid.addColumn(i -> items.indexOf(i) + 1, new NumberRenderer() );
        grid.addColumn(Url::getId, new NumberRenderer());
        grid.addColumn(Url::getLongUrl, new TextRenderer());
        grid.addColumn(Url::getShortUrl, new TextRenderer());

        layout.addComponent(grid);
        addComponent(layout);

    }
}
