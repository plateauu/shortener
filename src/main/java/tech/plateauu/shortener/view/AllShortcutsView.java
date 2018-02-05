package tech.plateauu.shortener.view;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.NumberRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import tech.plateauu.shortener.model.Url;
import tech.plateauu.shortener.read.UrlReader;
import tech.plateauu.shortener.write.WriterController;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = AllShortcutsView.VIEW_NAME)
@UIScope
class AllShortcutsView extends VerticalLayout implements View {

    static final String VIEW_NAME = "allShortcuts";
    private static final String ORDER_SORTER = "id";

    private final UrlReader reader;
    private final WriterController writer;

    @Autowired
    AllShortcutsView(UrlReader reader, WriterController writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @PostConstruct
    void init() {

        addComponent(new Label("All shortcuts:"));
        final VerticalLayout layout = new VerticalLayout();
        final Grid<Url> grid = new Grid<>();
        configureGrid(grid);
        layout.addComponent(grid);
        final HorizontalLayout addComponent = configureAddComponent(grid);
        layout.addComponent(addComponent);
        layout.setExpandRatio(addComponent, 1f);
        layout.setExpandRatio(grid, 1f);
        addComponent(layout);
    }

    private HorizontalLayout configureAddComponent(Grid<Url> grid) {
        final HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();

        final Label label = new Label("Short new address:");
        label.setContentMode(ContentMode.TEXT);

        final TextArea urlText = new TextArea();
        urlText.setId("text");
//        urlText.setHeight(1, Unit.CM);
        urlText.setSizeFull();

        final Button addNewButton = new Button("Add new");
        addNewButton.addClickListener(e -> {
            writer.addUrl(Url.of(null, urlText.getOptionalValue()
                    .orElseThrow(() -> new IllegalStateException("No Url value added"))));
            grid.getDataProvider().refreshAll();
        });

        //TODO do pokminienia
        layout.addComponent(label);
        layout.addComponent(urlText);
        layout.addComponent(addNewButton);
        layout.setExpandRatio(label, 0.2f);
        layout.setExpandRatio(urlText, 1.0f);
        layout.setExpandRatio(addNewButton, 0.2f);
        return layout;
    }

    private void configureGrid(Grid<Url> grid) {
        final List<Url> items = reader.findAll();
        final ListDataProvider<Url> dataProvider = DataProvider.ofCollection(items);

        dataProvider.addDataProviderListener(l -> {
            final List<Url> itemsAfterUpdate = reader.findAll();
            grid.setDataProvider(DataProvider.ofCollection(itemsAfterUpdate));
            grid.setHeightByRows(itemsAfterUpdate.size());
            grid.sort(ORDER_SORTER);
        });

        grid.setDataProvider(dataProvider);
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.setColumnResizeMode(ColumnResizeMode.SIMPLE);
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setHeightMode(HeightMode.ROW);
        grid.setHeightByRows(items.size());
        configureColumns(grid);
        grid.sort(ORDER_SORTER);
    }

    private void configureColumns(Grid<Url> grid) {

        grid.addColumn(Url::getId, new NumberRenderer())
                .setId(ORDER_SORTER)
                .setCaption("Id")
                .setHidable(true)
                .setResizable(true)
                .setWidth(100)
                .setMinimumWidthFromContent(true);

        grid.addColumn(url -> {
            final String longUrl = url.getLongUrl();
            return "<a href='" + longUrl + "' target='_blank'>" + longUrl + "</a>";
        }, new HtmlRenderer())
                .setId("longUrl")
                .setCaption("longUrl")
                .setHidable(true)
                .setResizable(true)
                .setMinimumWidthFromContent(true);

        grid.addColumn(url -> {
            final String shortUrl = url.getShortUrl();
            return "<a href='" + shortUrl + "' target='_blank'>" + shortUrl + "</a>";
        }, new HtmlRenderer())
                .setId("shortUrl")
                .setCaption("shortUrl")
                .setHidable(true)
                .setResizable(true)
                .setMinimumWidthFromContent(true);
    }
}
