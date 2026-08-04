package uk.co.stefirby.java.features.api

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.test.web.servlet.client.RestTestClient
import spock.lang.Specification

/**
 * STAGE-9-AC-03: while spring.threads.virtual.enabled=true, when the
 * thread-info endpoint is requested, the API serves the request on a
 * virtual thread — the thread report comes from the concurrency package's
 * static method behind a thin controller. Runs against a real server
 * (RANDOM_PORT) so the request is handled by Tomcat's virtual-thread
 * executor, not the test thread.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class ConcurrencyControllerSpec extends Specification {

    @Autowired
    RestTestClient client

    @Autowired
    Environment environment

    def "STAGE-9-AC-03: GET /api/concurrency/thread-info is served on a virtual thread"() {
        given: "spring.threads.virtual.enabled is true"
            assert environment.getProperty("spring.threads.virtual.enabled", Boolean) == Boolean.TRUE

        when: "the thread-info endpoint is requested"
            def body = client.get()
                    .uri("/api/concurrency/thread-info")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(String)
                    .returnResult()
                    .responseBody
            def report = new JsonSlurper().parseText(body)

        then: "the API serves the request on a virtual thread"
            report.virtual == true

        and: "the report names the serving thread, as reported by the concurrency static method"
            report.containsKey("threadName")
    }
}
