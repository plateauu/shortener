package tech.plateauu.shortener.view;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.context.annotation.Primary;

@Theme("valo")
@SpringUI(path = "/start")
@SpringViewDisplay
class MainUI extends UI implements ViewDisplay {

    private static final String ALL_SHORTCUTS_BUTTON = "All shortcuts";

    private final Panel display;

    protected MainUI() {
        this.display = new Panel();
        this.display.setSizeFull();
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        setContent(layout);


        CssLayout bar = new CssLayout();
        bar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        bar.addComponent(createButton(ALL_SHORTCUTS_BUTTON, AllShortcutsView.VIEW_NAME));
        layout.addComponent(bar);
        layout.addComponent(display);
        layout.setExpandRatio(display, 1.0f);
    }

    private Button createButton(String caption, String viewName) {
        final Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(e -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    @Override
    public void showView(View view) {
        display.setContent(view.getViewComponent());
    }
}
