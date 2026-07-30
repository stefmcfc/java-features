package uk.co.stefirby.java.features.optional

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * Covers STAGE-5-AC-01 — Optional.or() (9): a team with no players falls
 * back to the league-wide top scorer; a team with players keeps its own.
 */
class OptionalOrExampleSpec extends Specification {

    def "STAGE-5-AC-01: topScorerOfTeam is empty for a team with no players"() {
        expect: "an unknown team's top scorer lookup is empty"
            OptionalOrExample.topScorerOfTeam("UnknownFC").isEmpty()
    }

    def "STAGE-5-AC-01: topScorerOrLeagueTop falls back to the league-wide top scorer for a team with no players"() {
        given: "the league-wide top scorer, computed directly"
            def expected = PremierLeagueDataBase.getAllPlayers().max { it.goals() }

        when: "topScorerOrLeagueTop is called for a team with no players"
            def result = OptionalOrExample.topScorerOrLeagueTop("UnknownFC")

        then: "the fallback league-wide top scorer is returned"
            result == Optional.of(expected)
    }

    def "STAGE-5-AC-01: topScorerOrLeagueTop keeps a team's own top scorer when it has one"() {
        when: "topScorerOrLeagueTop is called for a team with players"
            def result = OptionalOrExample.topScorerOrLeagueTop("Liverpool")

        then: "that team's own top scorer is returned, not the league-wide fallback"
            result.get().name() == "Mohamed Salah"
    }
}
