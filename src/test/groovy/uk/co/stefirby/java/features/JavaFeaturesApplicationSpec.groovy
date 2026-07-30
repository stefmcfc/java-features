package uk.co.stefirby.java.features

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

/**
 * STAGE-1-AC-01: the test JVM shall report feature version 21.
 * STAGE-1-AC-04: the application context shall load successfully.
 */
@SpringBootTest
class JavaFeaturesApplicationSpec extends Specification {

    @Autowired
    ApplicationContext applicationContext

    def "test JVM reports feature version 21"() {
        expect: "Runtime.version() reports feature 21"
            Runtime.version().feature() == 21
    }

    def "application context loads"() {
        expect: "the Spring application context is not null"
            applicationContext != null
    }
}
