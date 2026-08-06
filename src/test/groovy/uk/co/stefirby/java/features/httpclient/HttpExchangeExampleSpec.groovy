package uk.co.stefirby.java.features.httpclient

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import spock.lang.Specification
import uk.co.stefirby.java.features.streams.StreamToListExample

/**
 * STAGE-10-AC-04 (declarative variant): when the same request is made via a
 * declarative @HttpExchange interface, the client shall return the same
 * payload as the raw JDK HttpClient and RestClient variants — asserted
 * against the stage-3 static method all three routes ultimately serve.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpExchangeExampleSpec extends Specification {

    @LocalServerPort
    int port

    def "STAGE-10-AC-04: the declarative @HttpExchange variant returns the same top-scorers payload"() {
        when: "the same request is made via the declarative @HttpExchange interface"
            def payload = HttpExchangeExample.topScorers("http://localhost:${port}")

        then: "the client returns the same payload as the endpoint's stage-3 source method"
            payload == StreamToListExample.topScorerNames()
    }
}
