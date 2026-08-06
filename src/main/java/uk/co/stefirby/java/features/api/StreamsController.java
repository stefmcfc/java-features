package uk.co.stefirby.java.features.api;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.streams.CollectorsFilteringExample;
import uk.co.stefirby.java.features.streams.CollectorsFlatMappingExample;
import uk.co.stefirby.java.features.streams.CollectorsGroupingByExample;
import uk.co.stefirby.java.features.streams.StreamToListExample;

/**
 * Java 21 / Spring Boot 4.1: thin REST layer exposing the stage-3 and
 * stage-11 Stream API examples over HTTP — no feature logic lives here,
 * each handler delegates straight to the static method the console demo
 * calls, and maps a negative {@code minGoals} onto a 400 with an RFC 9457
 * {@code ProblemDetail} body.
 */
@RestController
@RequestMapping("/api/streams")
public class StreamsController {

    @GetMapping("/top-scorers")
    public List<String> topScorers() {
        return StreamToListExample.topScorerNames();
    }

    @GetMapping("/grouped-by-team")
    public Map<String, List<Player>> groupedByTeam() {
        return CollectorsGroupingByExample.playersGroupedByTeam();
    }

    @GetMapping("/scorers-by-team")
    public Map<String, List<String>> scorersByTeam() {
        return CollectorsFlatMappingExample.scorerNamesByTeam();
    }

    @GetMapping("/players-by-position")
    public Map<String, List<Player>> playersByPosition(@RequestParam(defaultValue = "0") int minGoals) {
        if (minGoals < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "minGoals must not be negative: " + minGoals);
        }
        return CollectorsFilteringExample.playersByPositionWithMinGoals(minGoals);
    }
}
