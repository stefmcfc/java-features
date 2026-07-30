package uk.co.stefirby.java.features.streams

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * Covers STAGE-3-AC-08 — Collectors.groupingBy with
 * Collectors.toUnmodifiableList() (10) groups all PL players by team
 * name into a map of unmodifiable lists (the logic stage 4's
 * GET /api/streams/grouped-by-team delegates to).
 */
class CollectorsGroupingByExampleSpec extends Specification {

    def "STAGE-3-AC-08: playersGroupedByTeam groups every player under its team name"() {
        given: "every player grouped by team name via the dataset directly"
            def expected = PremierLeagueDataBase.getAllPlayers().groupBy { it.team() }

        when: "playersGroupedByTeam is called"
            def result = CollectorsGroupingByExample.playersGroupedByTeam()

        then: "the result matches the direct grouping"
            result == expected
    }

    def "STAGE-3-AC-08: each team's list of players is unmodifiable"() {
        given: "the grouped result"
            def result = CollectorsGroupingByExample.playersGroupedByTeam()

        when: "a team's player list is mutated"
            result["Arsenal"].remove(0)

        then: "the list rejects the mutation"
            thrown(UnsupportedOperationException)
    }
}
