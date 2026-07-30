package uk.co.stefirby.java.features.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.optional.OptionalOrExample;

/**
 * Java 21 / Spring Boot 4.1: thin REST layer exposing the stage-5 Optional
 * example over HTTP — no feature logic lives here, the handler delegates
 * straight to the static method the console demo calls, and maps an absent
 * result onto a 404 with an RFC 9457 {@code ProblemDetail} body.
 */
@RestController
@RequestMapping("/api/optional")
public class OptionalController {

    @GetMapping("/team-top-scorer")
    public Player teamTopScorer(@RequestParam String team) {
        return OptionalOrExample.topScorerOfTeam(team)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown team: " + team));
    }
}
