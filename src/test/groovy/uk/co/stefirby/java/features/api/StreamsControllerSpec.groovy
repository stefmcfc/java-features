package uk.co.stefirby.java.features.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.test.web.servlet.client.RestTestClient
import spock.lang.Specification
import uk.co.stefirby.java.features.data.Player
import uk.co.stefirby.java.features.streams.CollectorsGroupingByExample
import uk.co.stefirby.java.features.streams.StreamToListExample

/**
 * STAGE-4-AC-01, STAGE-4-AC-02, STAGE-4-AC-05: integration-level coverage of
 * the stage-4 streams endpoints, using Spring Framework 7's RestTestClient
 * (the Boot 4.1-idiomatic test client) to prove each endpoint delegates
 * straight to its stage-3 static method.
 */
@SpringBootTest
@AutoConfigureRestTestClient
class StreamsControllerSpec extends Specification {

    @Autowired
    RestTestClient client

    def "STAGE-4-AC-01: GET /api/streams/top-scorers responds 200 with the stage-3 top-scorers result"() {
        given:
        def expected = StreamToListExample.topScorerNames()

        expect:
        client.get()
                .uri("/api/streams/top-scorers")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<String>>() {})
                .isEqualTo(expected)
    }

    def "STAGE-4-AC-02: GET /api/streams/grouped-by-team responds 200 with the stage-3 grouped-by-team result"() {
        given:
        def expected = CollectorsGroupingByExample.playersGroupedByTeam()

        expect:
        client.get()
                .uri("/api/streams/grouped-by-team")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Map<String, List<Player>>>() {})
                .isEqualTo(expected)
    }
}
