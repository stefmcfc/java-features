package uk.co.stefirby.java.features.streams

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * Covers STAGE-3-AC-02 — Stream.mapMulti() (16) flattens each PL team into
 * its players' names.
 */
class StreamMapMultiExampleSpec extends Specification {

    def "STAGE-3-AC-02: playerNamesFlattenedFromTeams flattens every team into its players' names"() {
        given: "each team's players' names, in team order then player order"
            def expected = PremierLeagueDataBase.getAllTeams().collectMany { team ->
                PremierLeagueDataBase.getAllPlayers()
                        .findAll { it.team() == team.name() }*.name()
            }

        when: "playerNamesFlattenedFromTeams is called"
            def result = StreamMapMultiExample.playerNamesFlattenedFromTeams()

        then: "the result matches the expected flattening"
            result == expected

        and: "every player is represented exactly once"
            result.size() == PremierLeagueDataBase.getAllPlayers().size()
    }
}
