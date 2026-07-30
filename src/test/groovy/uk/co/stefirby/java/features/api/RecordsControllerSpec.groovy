package uk.co.stefirby.java.features.api

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.client.RestTestClient
import spock.lang.Specification
import uk.co.stefirby.java.features.records.PlayerSummaryExample

/**
 * STAGE-7-AC-04a, STAGE-7-AC-04b: integration-level coverage of the stage-7
 * records endpoint, using Spring Framework 7's RestTestClient to prove it
 * delegates to the stage-7 PlayerSummaryExample.findById() lookup and maps
 * an unknown id to an RFC 9457 ProblemDetail.
 */
@SpringBootTest
@AutoConfigureRestTestClient
class RecordsControllerSpec extends Specification {

    @Autowired
    RestTestClient client

    def "STAGE-7-AC-04a: GET /api/records/player/{id} responds 200 with the stage-7 example's result for a known id"() {
        given: "the stage-7 example's PlayerSummary for a known id"
            def expected = PlayerSummaryExample.findById(0).get()

        expect: "GET /api/records/player/0 responds 200 with that same PlayerSummary as JSON"
            client.get()
                    .uri("/api/records/player/0")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(PlayerSummaryExample.PlayerSummary)
                    .isEqualTo(expected)
    }

    def "STAGE-7-AC-04b: GET /api/records/player/{id} responds 404 with a ProblemDetail body for an unknown id"() {
        when: "GET /api/records/player/-1 is requested"
            def body = client.get()
                    .uri("/api/records/player/-1")
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody(String)
                    .returnResult()
                    .responseBody
            def problem = new JsonSlurper().parseText(body)

        then: "the body is an RFC 9457 ProblemDetail reporting the unknown id"
            problem.status == 404
            problem.detail.contains("-1")
    }
}
