package uk.co.stefirby.java.features.data

import spock.lang.Specification

/**
 * STAGE-2-AC-01: the data.Player record shall expose name, team, position,
 * nationality, goals, assists, appearances, and minutesPlayed as components.
 */
class PlayerSpec extends Specification {

    def "Player is a record"() {
        expect: "Player is declared as a record"
            Player.isRecord()
    }

    def "Player exposes the expected components in order"() {
        expect: "the record components are name, team, position, nationality, goals, assists, appearances, minutesPlayed"
            Player.recordComponents*.name == [
                    "name", "team", "position", "nationality",
                    "goals", "assists", "appearances", "minutesPlayed",
            ]
    }

    def "Player components are readable through their accessors"() {
        given: "a Player built from known values"
            def player = new Player("Erling Haaland", "Manchester City", "Forward",
                    "Norway", 27, 5, 31, 2552)

        expect: "each accessor returns the value it was constructed with"
            player.name() == "Erling Haaland"
            player.team() == "Manchester City"
            player.position() == "Forward"
            player.nationality() == "Norway"
            player.goals() == 27
            player.assists() == 5
            player.appearances() == 31
            player.minutesPlayed() == 2552
    }
}
