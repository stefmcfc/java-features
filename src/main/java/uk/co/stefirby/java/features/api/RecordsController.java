package uk.co.stefirby.java.features.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.co.stefirby.java.features.records.PlayerSummaryExample;
import uk.co.stefirby.java.features.records.PlayerSummaryExample.PlayerSummary;

/**
 * Java 21 / Spring Boot 4.1: thin REST layer exposing the stage-7 records
 * example over HTTP — no feature logic lives here, the handler delegates
 * straight to the static method the console demo calls, and maps an unknown
 * id onto a 404 with an RFC 9457 {@code ProblemDetail} body.
 */
@RestController
@RequestMapping("/api/records")
public class RecordsController {

    @GetMapping("/player/{id}")
    public PlayerSummary player(@PathVariable int id) {
        return PlayerSummaryExample.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown player id: " + id));
    }
}
