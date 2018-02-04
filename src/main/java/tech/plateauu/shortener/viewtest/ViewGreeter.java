package tech.plateauu.shortener.viewtest;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
class ViewGreeter {

    String sayHello(){
        return "Hello from a viewtest scoped bean " + toString();
    }

}
