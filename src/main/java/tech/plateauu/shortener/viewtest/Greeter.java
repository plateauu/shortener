package tech.plateauu.shortener.viewtest;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent
@UIScope
class Greeter {

    String sayHello() {
        return "Hello from bean " + toString();
    }
}
