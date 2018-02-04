package tech.plateauu.shortener.viewtest;

import com.google.common.collect.Lists;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.components.grid.HeaderRow;
import org.springframework.beans.factory.annotation.Autowired;
import tech.plateauu.shortener.read.UrlReader;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = MyGridView.VIEW_NAME)
@UIScope
class MyGridView extends VerticalLayout implements View {
    static final String VIEW_NAME = "grid";

    private final UrlReader urlReader;

    @Autowired
    MyGridView(UrlReader urlReader) {
        this.urlReader = urlReader;
    }

    @PostConstruct
    void init() {
        addComponent(new Label("Grid:"));
        VerticalLayout layout = new VerticalLayout();

        List<Person> people = Lists.newArrayList();
        people.add(new Person("Nicolaus Copernicus", 1543));
        people.add(new Person("Galileo Galilei", 1564));
        people.add(new Person("Johanes Kepler", 1564));


        Grid<Person> grid = new Grid<>();
        grid.setItems(people);
        Binder<Person> binder = grid.getEditor().getBinder();
        TextField field = new TextField();
        field.setValueChangeMode(ValueChangeMode.LAZY);
        Binder.Binding<Person, String> binding = binder.bind(field, Person::getName, Person::setName);

        grid.addColumn(Person::getName)
                .setId("name")
                .setCaption("Name")
                .setEditorBinding(binding)
                .setEditable(true)
                .setHidable(true)
                .setMinimumWidth(100.0)
                .setResizable(true)
                .setEditorComponent(field, Person::setName);


        grid.addColumn(Person::getYear)
                .setId("year")
                .setHidable(true)
                .setMinimumWidth(50)
                .setResizable(true)
                .setCaption("Year of birth");

        HeaderRow groupingHeaderRow = grid.prependHeaderRow();
        groupingHeaderRow.join(
                groupingHeaderRow.getCell("name"),
                groupingHeaderRow.getCell("year"))
                .setText("People:");

        FooterRow prepend = grid.prependFooterRow();
        prepend.getCell("name")
                .setText("Total numbers of people");
        prepend.getCell("year")
                .setText(String.valueOf(people.size()));

        grid.setSelectionMode(Grid.SelectionMode.NONE);

        grid.addItemClickListener(e -> {
            String clickedColumn = e.getColumn().getCaption();
            Person clickedItem = e.getItem();
            Notification.show("items clicked: \n" + clickedColumn + "\n Person: " + clickedItem.getName(), Notification.Type.HUMANIZED_MESSAGE);
        });


        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(e -> {
            try {
                binder.writeBean(e.getBean());
                refreshGrid(grid);
            } catch (ValidationException e1) {
                e1.printStackTrace();
            }

        });

        grid.setColumnResizeMode(ColumnResizeMode.SIMPLE);
        grid.setHeightMode(HeightMode.ROW);
        extendGridSize(people, grid);
        grid.setWidth(100, Unit.PERCENTAGE);

        Button addNew = new Button("Add new");
        addNew.addClickListener(e -> {
            people.add(new Person());
            extendGridSize(people, grid);
            prepend.getCell("year").setText(String.valueOf(people.size()));
            refreshGrid(grid);
        });

        layout.addComponent(grid);
        layout.addComponent(addNew);
        addComponent(layout);
    }

    private void extendGridSize(List<Person> people, Grid<Person> grid) {
        grid.setHeightByRows(people.size() + 1);
    }

    private void refreshGrid(Grid<Person> grid) {
        grid.getDataProvider().refreshAll();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
