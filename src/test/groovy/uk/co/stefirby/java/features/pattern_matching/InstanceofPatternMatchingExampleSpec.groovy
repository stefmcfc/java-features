package uk.co.stefirby.java.features.pattern_matching

import spock.lang.Specification
import uk.co.stefirby.java.features.data.Match
import uk.co.stefirby.java.features.data.Player
import uk.co.stefirby.java.features.data.Team

import java.time.LocalDate

/**
 * STAGE-8-AC-02: when the instanceof pattern-matching (16) example
 * classifies an object that may be a Player, Team, or Match, it describes
 * it via pattern variables with no explicit casts.
 */
class InstanceofPatternMatchingExampleSpec extends Specification {

    def "STAGE-8-AC-02: describe identifies a Player via a pattern variable"() {
        given: "a Player from the domain model"
            def player = new Player("Bukayo Saka", "Arsenal", "Forward", "England", 12, 13, 35, 2940)

        when: "the object is classified"
            def description = InstanceofPatternMatchingExample.describe(player)

        then: "it is described using the Player's components"
            description == "Player Bukayo Saka plays Forward for Arsenal"
    }

    def "STAGE-8-AC-02: describe identifies a Team via a pattern variable"() {
        given: "a Team from the domain model"
            def team = new Team("Arsenal", "Emirates Stadium", "Mikel Arteta", 1886)

        when: "the object is classified"
            def description = InstanceofPatternMatchingExample.describe(team)

        then: "it is described using the Team's components"
            description == "Team Arsenal play at Emirates Stadium under Mikel Arteta"
    }

    def "STAGE-8-AC-02: describe identifies a Match via a pattern variable"() {
        given: "a Match from the domain model"
            def match = new Match("Arsenal", "Tottenham Hotspur", 3, 0, LocalDate.of(2026, 2, 7))

        when: "the object is classified"
            def description = InstanceofPatternMatchingExample.describe(match)

        then: "it is described using the Match's components"
            description == "Match Arsenal 3-0 Tottenham Hotspur on 2026-02-07"
    }

    def "STAGE-8-AC-02: describe falls back for an object outside the domain model"() {
        when: "an unrelated object is classified"
            def description = InstanceofPatternMatchingExample.describe(42)

        then: "it is reported as unrecognised"
            description == "Unrecognised object: 42"
    }
}
