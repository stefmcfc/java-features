package uk.co.stefirby.java.features.api

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.client.RestTestClient
import spock.lang.Specification
import uk.co.stefirby.java.features.data.Player
import uk.co.stefirby.java.features.optional.OptionalOrExample

/**
 * STAGE-5-AC-05a, STAGE-5-AC-05b: integration-level coverage of the stage-5
 * optional endpoint, using Spring Framework 7's RestTestClient to prove it
 * delegates to the stage-5 Optional<Player>-returning example and maps an
 * absent result to an RFC 9457 ProblemDetail.
 */
@SpringBootTest
@AutoConfigureRestTestClient
class OptionalControllerSpec extends Specification {

    @Autowired
    RestTestClient client

    def "STAGE-5-AC-05a: GET /api/optional/team-top-scorer responds 200 with the stage-5 example's result for a known team"() {
        given: "the stage-5 example's result for a known team"
            def expected = OptionalOrExample.topScorerOfTeam("Liverpool").get()

        expect: "GET /api/optional/team-top-scorer?team=Liverpool responds 200 with that same player as JSON"
            client.get()
                    .uri("/api/optional/team-top-scorer?team=Liverpool")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(Player)
                    .isEqualTo(expected)
    }

    def "STAGE-5-AC-05b: GET /api/optional/team-top-scorer responds 404 with a ProblemDetail body for an unknown team"() {
        when: "GET /api/optional/team-top-scorer?team=UnknownFC is requested"
            def body = client.get()
                    .uri("/api/optional/team-top-scorer?team=UnknownFC")
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody(String)
                    .returnResult()
                    .responseBody
            def problem = new JsonSlurper().parseText(body)

        then: "the body is an RFC 9457 ProblemDetail reporting the unknown team"
            problem.status == 404
            problem.detail.contains("UnknownFC")
    }
}
