package uk.co.stefirby.java.features.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.test.web.servlet.client.RestTestClient
import groovy.json.JsonSlurper
import spock.lang.Specification
import uk.co.stefirby.java.features.data.Player
import uk.co.stefirby.java.features.streams.CollectorsFilteringExample
import uk.co.stefirby.java.features.streams.CollectorsFlatMappingExample
import uk.co.stefirby.java.features.streams.CollectorsGroupingByExample
import uk.co.stefirby.java.features.streams.StreamToListExample

/**
 * STAGE-4-AC-01, STAGE-4-AC-02, STAGE-4-AC-05, STAGE-11-AC-07,
 * STAGE-11-AC-08, STAGE-11-AC-09: integration-level coverage of the stage-4
 * and stage-11 streams endpoints, using Spring Framework 7's RestTestClient
 * (the Boot 4.1-idiomatic test client) to prove each endpoint delegates
 * straight to its example's static method.
 */
@SpringBootTest
@AutoConfigureRestTestClient
class StreamsControllerSpec extends Specification {

    @Autowired
    RestTestClient client

    def "STAGE-4-AC-01: GET /api/streams/top-scorers responds 200 with the stage-3 top-scorers result"() {
        given: "the stage-3 top-scorers result"
            def expected = StreamToListExample.topScorerNames()

        expect: "GET /api/streams/top-scorers responds 200 with that same result as JSON"
            client.get()
                    .uri("/api/streams/top-scorers")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(new ParameterizedTypeReference<List<String>>() {})
                    .isEqualTo(expected)
    }

    def "STAGE-4-AC-02: GET /api/streams/grouped-by-team responds 200 with the stage-3 grouped-by-team result"() {
        given: "the stage-3 grouped-by-team result"
            def expected = CollectorsGroupingByExample.playersGroupedByTeam()

        expect: "GET /api/streams/grouped-by-team responds 200 with that same result as JSON"
            client.get()
                    .uri("/api/streams/grouped-by-team")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(new ParameterizedTypeReference<Map<String, List<Player>>>() {})
                    .isEqualTo(expected)
    }

    def "STAGE-11-AC-07: GET /api/streams/scorers-by-team responds 200 with the flat-mapping example's result"() {
        given: "the flat-mapping example's team-to-scorer-names result"
            def expected = CollectorsFlatMappingExample.scorerNamesByTeam()

        expect: "GET /api/streams/scorers-by-team responds 200 with that same result as JSON"
            client.get()
                    .uri("/api/streams/scorers-by-team")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
                    .isEqualTo(expected)
    }

    def "STAGE-11-AC-08: GET /api/streams/players-by-position responds 200 with the filtering example's result for the given threshold"() {
        given: "the filtering example's result for a 5-goal threshold"
            def expected = CollectorsFilteringExample.playersByPositionWithMinGoals(5)

        expect: "GET /api/streams/players-by-position?minGoals=5 responds 200 with that same result as JSON"
            client.get()
                    .uri("/api/streams/players-by-position?minGoals=5")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(new ParameterizedTypeReference<Map<String, List<Player>>>() {})
                    .isEqualTo(expected)
    }

    def "STAGE-11-AC-08: GET /api/streams/players-by-position without minGoals defaults the threshold to 0"() {
        given: "the filtering example's result for the default 0-goal threshold"
            def expected = CollectorsFilteringExample.playersByPositionWithMinGoals(0)

        expect: "GET /api/streams/players-by-position with no minGoals responds 200 with the 0-threshold result"
            client.get()
                    .uri("/api/streams/players-by-position")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(new ParameterizedTypeReference<Map<String, List<Player>>>() {})
                    .isEqualTo(expected)
    }

    def "STAGE-11-AC-09: GET /api/streams/players-by-position with a negative minGoals responds 400 with a ProblemDetail body"() {
        when: "GET /api/streams/players-by-position?minGoals=-1 is requested"
            def body = client.get()
                    .uri("/api/streams/players-by-position?minGoals=-1")
                    .exchange()
                    .expectStatus().isBadRequest()
                    .expectBody(String)
                    .returnResult()
                    .responseBody
            def problem = new JsonSlurper().parseText(body)

        then: "the body is an RFC 9457 ProblemDetail reporting the bad minGoals value"
            problem.status == 400
            problem.detail.contains("-1")
    }
}
