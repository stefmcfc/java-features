package uk.co.stefirby.java.features.data

import spock.lang.Specification

import java.time.LocalDate

/**
 * STAGE-2-AC-03: the data.Match record shall expose homeTeam, awayTeam,
 * homeGoals, awayGoals, and date as components.
 */
class MatchSpec extends Specification {

    def "Match is a record"() {
        expect:
        Match.isRecord()
    }

    def "Match exposes the expected components in order"() {
        expect:
        Match.recordComponents*.name == [
                "homeTeam", "awayTeam", "homeGoals", "awayGoals", "date",
        ]
    }

    def "Match components are readable through their accessors"() {
        given:
        def match = new Match("Liverpool", "Arsenal", 2, 1,
                LocalDate.of(2025, 8, 17))

        expect:
        match.homeTeam() == "Liverpool"
        match.awayTeam() == "Arsenal"
        match.homeGoals() == 2
        match.awayGoals() == 1
        match.date() == LocalDate.of(2025, 8, 17)
    }
}
