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
        given:
        def expected = PremierLeagueDataBase.getAllPlayers().groupBy { it.team() }

        when:
        def result = CollectorsGroupingByExample.playersGroupedByTeam()

        then:
        result == expected
    }

    def "STAGE-3-AC-08: each team's list of players is unmodifiable"() {
        given:
        def result = CollectorsGroupingByExample.playersGroupedByTeam()

        when:
        result["Arsenal"].remove(0)

        then:
        thrown(UnsupportedOperationException)
    }
}
