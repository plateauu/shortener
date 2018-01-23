package tech.plateauu.shortener.view;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderRow;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SpringView(name = MyGridView.VIEW_NAME)
@UIScope
class MyGridView extends VerticalLayout implements View {
    static final String VIEW_NAME = "grid";

    @PostConstruct
    void init() {
        addComponent(new Label("Grid:"));
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        List<Person> people = Arrays.asList(
                new Person("Nicolaus Copernicus", 1543),
                new Person("Galileo Galilei", 1564),
                new Person("Johanes Kepler", 1564));

        Grid<Person> grid = new Grid<>();
        grid.setItems(people);

        grid.addColumn(Person::getName)
                .setCaption("Name")
//                .setEditable(true)
                .setHidable(true)
                .setMinimumWidth(100.0)
                .setResizable(true);

        grid.addColumn(Person::getYear).setCaption("Year of birth");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addItemClickListener(e -> {
            String clickedColumn = e.getColumn().getCaption();
            Person clickedItem = e.getItem();
            Notification.show("items clicked: \n"  + clickedColumn + "\n Person: " + clickedItem.getName(), Notification.Type.HUMANIZED_MESSAGE);
        });

        grid.setColumnResizeMode(ColumnResizeMode.SIMPLE);
        grid.setSizeFull();
        layout.addComponent(grid);

        addComponent(layout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
