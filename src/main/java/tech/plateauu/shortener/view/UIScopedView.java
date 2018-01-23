package tech.plateauu.shortener.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = UIScopedView.VIEW_NAME)
class UIScopedView  extends VerticalLayout implements View {

    static final String VIEW_NAME = "ui";

    @PostConstruct
    void init(){
        addComponent(new Label("This is A UI scoped view..."));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
