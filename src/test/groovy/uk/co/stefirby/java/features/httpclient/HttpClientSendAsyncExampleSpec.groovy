package uk.co.stefirby.java.features.httpclient

import groovy.json.JsonSlurper
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import spock.lang.Specification
import uk.co.stefirby.java.features.streams.StreamToListExample

import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

/**
 * STAGE-12-AC-01: when the HttpClient.sendAsync() (11) example issues an
 * asynchronous GET against the app's own stage-4 top-scorers endpoint, it
 * returns a CompletableFuture that completes with the response status and
 * body without blocking the calling thread. Runs against a real server
 * (RANDOM_PORT) so the example makes an actual HTTP call.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpClientSendAsyncExampleSpec extends Specification {

    @LocalServerPort
    int port

    def "STAGE-12-AC-01: sendAsync dispatches several requests without blocking the calling thread on any one response"() {
        given: "the base URI of the running instance"
            def baseUri = "http://localhost:${port}"

        when: "five async GET requests are issued back-to-back without waiting for any to finish"
            long start = System.nanoTime()
            List<CompletableFuture<HttpClientGetExample.Result>> futures = (1..5).collect {
                HttpClientSendAsyncExample.getAsync(baseUri, "/api/streams/top-scorers")
            }
            long dispatchElapsedMs = (System.nanoTime() - start) / 1_000_000

        then: "dispatching all five returns near-instantly, proving the calling thread was not blocked awaiting a response"
            dispatchElapsedMs < 500
            futures.every { it instanceof CompletableFuture }

        when: "the futures are awaited"
            def results = futures*.get(5, TimeUnit.SECONDS)

        then: "each future completes with the endpoint's response status and body"
            results.every { it.statusCode() == 200 }
            results.every { new JsonSlurper().parseText(it.body()) == StreamToListExample.topScorerNames() }
    }
}
