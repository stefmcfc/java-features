package uk.co.stefirby.java.features.streams

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * Covers STAGE-3-AC-03 — Collectors.teeing() (12) computes the league's
 * total goals and average goals per player in a single pass and returns
 * both together.
 */
class CollectorsTeeingExampleSpec extends Specification {

    def "STAGE-3-AC-03: leagueGoalStats returns total and average goals together"() {
        given: "the total and average goals computed directly from the dataset"
            def players = PremierLeagueDataBase.getAllPlayers()
            def expectedTotal = players.sum { it.goals() }
            def expectedAverage = expectedTotal / (double) players.size()

        when: "leagueGoalStats is called"
            def stats = CollectorsTeeingExample.leagueGoalStats()

        then: "both totals match the direct computation"
            stats.totalGoals() == expectedTotal
            Math.abs(stats.averageGoalsPerPlayer() - expectedAverage) < 1e-9
    }
}
