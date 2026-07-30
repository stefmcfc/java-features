package uk.co.stefirby.java.features.api;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.streams.CollectorsGroupingByExample;
import uk.co.stefirby.java.features.streams.StreamToListExample;

/**
 * Java 21 / Spring Boot 4.1: thin REST layer exposing the stage-3 Stream API
 * examples over HTTP — no feature logic lives here, each handler delegates
 * straight to the static method the console demo calls.
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
}
