package uk.co.stefirby.java.features.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.stefirby.java.features.random.MatchweekSimulatorExample;
import uk.co.stefirby.java.features.random.MatchweekSimulatorExample.SimulatedMatchweek;

/**
 * Java 17 / Spring Boot 4.1: thin REST layer exposing the stage-12
 * RandomGenerator matchweek simulator over HTTP — no feature logic lives
 * here, the handler delegates straight to the {@code random} package's
 * static methods, including the seed-minting logic used when none is
 * supplied on the query string.
 */
@RestController
@RequestMapping("/api/random")
public class RandomController {

    @GetMapping("/simulated-matchweek")
    public SimulatedMatchweek simulatedMatchweek(@RequestParam(required = false) Long seed) {
        long resolvedSeed = seed != null ? seed : MatchweekSimulatorExample.randomSeed();
        return MatchweekSimulatorExample.simulate(resolvedSeed);
    }
}
