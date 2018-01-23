package tech.plateauu.shortener.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = DefaultView.VIEW_NAME)
class DefaultView extends VerticalLayout implements View {

    static final String VIEW_NAME = "";

    @PostConstruct
    void init(){
        addComponent(new Label("This is default view"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
