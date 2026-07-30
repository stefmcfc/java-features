package uk.co.stefirby.java.features.api

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.client.RestTestClient
import spock.lang.Specification

/**
 * STAGE-4-AC-06, STAGE-4-AC-07: springdoc-openapi is wired in so every
 * controller in {@code api/} is documented automatically — the generated
 * document is reachable and valid, Swagger UI responds, and the document
 * tracks the real stage-4 controller paths rather than being hand-maintained.
 */
@SpringBootTest
@AutoConfigureRestTestClient
class OpenApiSpec extends Specification {

    @Autowired
    RestTestClient client

    def "STAGE-4-AC-06: GET /v3/api-docs returns an OpenAPI 3.x document"() {
        when: "GET /v3/api-docs is requested"
            def body = client.get()
                    .uri("/v3/api-docs")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(String)
                    .returnResult()
                    .responseBody
            def openApiDoc = new JsonSlurper().parseText(body)

        then: "the document declares an OpenAPI 3.x version"
            openApiDoc.openapi.startsWith("3.")
    }

    def "STAGE-4-AC-06: the default springdoc Swagger UI path responds successfully"() {
        expect: "GET /swagger-ui/index.html responds 200"
            client.get()
                    .uri("/swagger-ui/index.html")
                    .exchange()
                    .expectStatus().isOk()
    }

    def "STAGE-4-AC-07: GET /v3/api-docs lists both stage-4 endpoint paths"() {
        when: "GET /v3/api-docs is requested"
            def body = client.get()
                    .uri("/v3/api-docs")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(String)
                    .returnResult()
                    .responseBody
            def openApiDoc = new JsonSlurper().parseText(body)

        then: "both stage-4 endpoint paths are listed"
            openApiDoc.paths.keySet().containsAll(["/api/streams/top-scorers", "/api/streams/grouped-by-team"])
    }
}
