package tech.plateauu.shortener

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [ShortenerApplication.class]
)
class SpringContextTest extends Specification {

    @Autowired
    ApplicationContext applicationContext

}
