package uk.co.stefirby.java.features.api

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.client.RestTestClient
import spock.lang.Specification
import uk.co.stefirby.java.features.random.MatchweekSimulatorExample

/**
 * STAGE-12-AC-06, STAGE-12-AC-07: GET /api/random/simulated-matchweek
 * responds 200 with the simulator's fixtures as JSON including the seed;
 * repeated requests with the same seed return identical bodies, and an
 * omitted seed is picked by the API itself and included in the payload so
 * the simulation can be reproduced.
 */
@SpringBootTest
@AutoConfigureRestTestClient
class RandomControllerSpec extends Specification {

    @Autowired
    RestTestClient client

    def "STAGE-12-AC-06: GET /api/random/simulated-matchweek?seed=<n> responds 200 with the simulator's fixtures and that seed"() {
        given: "the simulator's own result for a known seed"
            def expected = MatchweekSimulatorExample.simulate(42L)

        when: "the endpoint is requested with that seed"
            def body = client.get()
                    .uri("/api/random/simulated-matchweek?seed=42")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(String)
                    .returnResult()
                    .responseBody
            def payload = new JsonSlurper().parseText(body)

        then: "the payload includes the requested seed"
            payload.seed == 42

        and: "the payload's fixtures match the simulator's own result for that seed"
            payload.fixtures.size() == expected.fixtures().size()
            payload.fixtures*.homeTeam == expected.fixtures()*.homeTeam()
            payload.fixtures*.awayTeam == expected.fixtures()*.awayTeam()
            payload.fixtures*.homeGoals == expected.fixtures()*.homeGoals()
            payload.fixtures*.awayGoals == expected.fixtures()*.awayGoals()
    }

    def "STAGE-12-AC-06: repeated requests with the same seed return identical bodies"() {
        when: "the endpoint is requested twice with the same seed"
            def first = client.get().uri("/api/random/simulated-matchweek?seed=99").exchange()
                    .expectStatus().isOk().expectBody(String).returnResult().responseBody
            def second = client.get().uri("/api/random/simulated-matchweek?seed=99").exchange()
                    .expectStatus().isOk().expectBody(String).returnResult().responseBody

        then: "both responses are byte-for-byte identical"
            first == second
    }

    def "STAGE-12-AC-07: GET /api/random/simulated-matchweek without a seed picks one itself and includes it in the payload"() {
        when: "the endpoint is requested with no seed"
            def body = client.get()
                    .uri("/api/random/simulated-matchweek")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(String)
                    .returnResult()
                    .responseBody
            def payload = new JsonSlurper().parseText(body)

        then: "the payload includes a seed"
            payload.seed != null

        and: "requesting that exact seed reproduces the same fixtures"
            def replayBody = client.get()
                    .uri("/api/random/simulated-matchweek?seed=${payload.seed}")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(String)
                    .returnResult()
                    .responseBody
            body == replayBody
    }
}
