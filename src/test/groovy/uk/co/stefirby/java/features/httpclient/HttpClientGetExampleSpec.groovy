package uk.co.stefirby.java.features.httpclient

import groovy.json.JsonSlurper
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import spock.lang.Specification
import uk.co.stefirby.java.features.streams.StreamToListExample

/**
 * STAGE-10-AC-01 and STAGE-10-AC-04 (raw JDK variant): when the
 * java.net.http.HttpClient (11) example issues a GET request against the
 * app's own stage-4 endpoint, it shall return the response status and body.
 * Runs against a real server (RANDOM_PORT) so the example makes an actual
 * HTTP call, exactly as it would from main against a running instance.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpClientGetExampleSpec extends Specification {

    @LocalServerPort
    int port

    def "STAGE-10-AC-01: the HttpClient example's GET returns the response status and body"() {
        when: "the HttpClient example issues a GET request against the app's own stage-4 endpoint"
            def result = HttpClientGetExample.get("http://localhost:${port}", "/api/streams/top-scorers")

        then: "it returns the response status"
            result.statusCode() == 200

        and: "it returns the response body — the stage-4 top-scorers payload as JSON"
            new JsonSlurper().parseText(result.body()) == StreamToListExample.topScorerNames()
    }

    def "STAGE-10-AC-04: the raw JDK HttpClient variant returns the top-scorers payload"() {
        when: "the same request is made via the raw JDK HttpClient"
            def result = HttpClientGetExample.get("http://localhost:${port}", "/api/streams/top-scorers")

        then: "the client returns the same payload as the endpoint's stage-3 source method"
            new JsonSlurper().parseText(result.body()) == StreamToListExample.topScorerNames()
    }
}
