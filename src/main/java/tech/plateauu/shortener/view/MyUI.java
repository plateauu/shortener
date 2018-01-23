package tech.plateauu.shortener.view;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme("valo")
@SpringUI(path = "/start")
@SpringViewDisplay
class MyUI extends UI implements ViewDisplay{

    private Panel springViewDisplay;

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        setContent(layout);

        CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        navigationBar.addComponent(createNavigationButton("View Scoped View", ViewScopedView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("UI Scoped View", UIScopedView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Grid View", MyGridView.VIEW_NAME));
        layout.addComponent(navigationBar);

        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();
        layout.addComponent(springViewDisplay);
        layout.setExpandRatio(springViewDisplay, 1.0f);

    }

    private Button createNavigationButton(String caption, String viewName) {
        final Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);

         button.addClickListener(event -> getUI()
                                            .getNavigator()
                                            .navigateTo(viewName));
        return button;
    }

    @Override
    public void showView(View view) {
        springViewDisplay.setContent(view.getViewComponent());
    }
}
