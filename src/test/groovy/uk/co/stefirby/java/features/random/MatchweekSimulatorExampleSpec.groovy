package uk.co.stefirby.java.features.random

import spock.lang.Specification

/**
 * STAGE-12-AC-04: when the RandomGenerator (17) example simulates a
 * matchweek of fixtures using a seeded generator obtained via
 * RandomGenerator.of(...) / RandomGeneratorFactory, the same seed always
 * produces the same scorelines, and every simulated score is clamped to a
 * plausible range via Math.clamp() (21).
 */
class MatchweekSimulatorExampleSpec extends Specification {

    def "STAGE-12-AC-04: the same seed always produces the same scorelines"() {
        when: "a matchweek is simulated twice with the same seed"
            def first = MatchweekSimulatorExample.simulateMatchweek(42L)
            def second = MatchweekSimulatorExample.simulateMatchweek(42L)

        then: "both runs produce identical fixtures"
            first == second
            !first.isEmpty()
    }

    def "STAGE-12-AC-04: different seeds can produce different scorelines"() {
        when: "a matchweek is simulated with two different seeds"
            def first = MatchweekSimulatorExample.simulateMatchweek(1L)
            def second = MatchweekSimulatorExample.simulateMatchweek(2L)

        then: "the two runs are not forced to match"
            first != second
    }

    def "STAGE-12-AC-04: every simulated score is clamped to a plausible range"() {
        when: "a matchweek is simulated"
            def fixtures = MatchweekSimulatorExample.simulateMatchweek(7L)

        then: "every home and away score falls within the plausible clamped range"
            fixtures.every { it.homeGoals() >= 0 && it.homeGoals() <= 6 }
            fixtures.every { it.awayGoals() >= 0 && it.awayGoals() <= 6 }
    }

    def "STAGE-12-AC-04: fixtures pair up real dataset teams"() {
        when: "a matchweek is simulated"
            def fixtures = MatchweekSimulatorExample.simulateMatchweek(7L)

        then: "no fixture pairs a team against itself"
            fixtures.every { it.homeTeam() != it.awayTeam() }
    }
}
