package tech.plateauu.shortener.viewtest;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = ViewScopedView.VIEW_NAME)
class ViewScopedView extends VerticalLayout implements View {

    static final String VIEW_NAME = "viewtest";

    @Autowired
    private final Greeter greeter;

    @Autowired
    private final ViewGreeter viewGreeter;

    ViewScopedView(Greeter greeter, ViewGreeter viewGreeter) {
        this.greeter = greeter;
        this.viewGreeter = viewGreeter;
    }

    @PostConstruct
    void init(){
        addComponent(new Label("This is viewtest scoped viewtest"));
        addComponent(new Label(greeter.sayHello()));
        addComponent(new Label(viewGreeter.sayHello()));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
