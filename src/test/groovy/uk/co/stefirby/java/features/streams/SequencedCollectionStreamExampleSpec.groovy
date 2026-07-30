package uk.co.stefirby.java.features.streams

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * Covers STAGE-3-AC-07 — the sequenced-collection stream view (21)
 * streams the teams via reversed() and returns them last-to-first.
 */
class SequencedCollectionStreamExampleSpec extends Specification {

    def "STAGE-3-AC-07: teamsLastToFirst returns the teams in reverse dataset order"() {
        when: "teamsLastToFirst is called"
            def result = SequencedCollectionStreamExample.teamsLastToFirst()

        then: "the result is the dataset's teams in reverse order"
            result == PremierLeagueDataBase.getAllTeams().reverse()
    }
}
