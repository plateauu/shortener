package tech.plateauu.shortener.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
class ViewGreeter {

    String sayHello(){
        return "Hello from a view scoped bean " + toString();
    }

}
