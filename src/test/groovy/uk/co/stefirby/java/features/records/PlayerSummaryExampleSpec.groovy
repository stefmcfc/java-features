package uk.co.stefirby.java.features.records

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * STAGE-7-AC-03a, STAGE-7-AC-03b: the records (16) example defines a record
 * whose compact constructor validates its components, rejecting invalid
 * values with IllegalArgumentException. Also covers the findById() lookup
 * the stage-7 REST endpoint delegates to.
 */
class PlayerSummaryExampleSpec extends Specification {

    def "STAGE-7-AC-03a: PlayerSummary is a record"() {
        expect: "PlayerSummary is declared as a record"
            PlayerSummaryExample.PlayerSummary.isRecord()
    }

    def "STAGE-7-AC-03a: PlayerSummary's compact constructor accepts valid components"() {
        when: "a PlayerSummary is built with non-negative goals and assists"
            def summary = new PlayerSummaryExample.PlayerSummary("Mohamed Salah", "Liverpool", 29, 18)

        then: "the record is constructed with the given values"
            summary.name() == "Mohamed Salah"
            summary.team() == "Liverpool"
            summary.goals() == 29
            summary.assists() == 18
    }

    def "STAGE-7-AC-03b: PlayerSummary's compact constructor rejects negative goals"() {
        when: "a PlayerSummary is built with negative goals"
            new PlayerSummaryExample.PlayerSummary("Test Player", "Test FC", -1, 0)

        then: "construction throws IllegalArgumentException"
            thrown(IllegalArgumentException)
    }

    def "STAGE-7-AC-03b: PlayerSummary's compact constructor rejects negative assists"() {
        when: "a PlayerSummary is built with negative assists"
            new PlayerSummaryExample.PlayerSummary("Test Player", "Test FC", 0, -1)

        then: "construction throws IllegalArgumentException"
            thrown(IllegalArgumentException)
    }

    def "findById returns the matching player as a PlayerSummary for a known id"() {
        given: "the player at index 0 in the dataset"
            def expected = PremierLeagueDataBase.getAllPlayers().first()

        when: "findById(0) is called"
            def result = PlayerSummaryExample.findById(0)

        then: "the result is present and matches that player's stats"
            result.isPresent()
            result.get().name() == expected.name()
            result.get().team() == expected.team()
            result.get().goals() == expected.goals()
            result.get().assists() == expected.assists()
    }

    def "findById returns empty for an unknown id"() {
        expect: "findById(-1) returns empty"
            PlayerSummaryExample.findById(-1).isEmpty()
    }
}
