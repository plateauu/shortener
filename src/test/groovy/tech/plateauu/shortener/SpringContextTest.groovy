package tech.plateauu.shortener

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Profile
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [ShortenerApplication.class])
@Profile("test")
class SpringContextTest extends Specification {

    @Autowired
    ApplicationContext applicationContext

    def 'application context starts'() {
        expect:
        println applicationContext.displayName
    }
}
