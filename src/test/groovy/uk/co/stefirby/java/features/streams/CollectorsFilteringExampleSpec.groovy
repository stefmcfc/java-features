package uk.co.stefirby.java.features.streams

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * Covers STAGE-11-AC-02 — Collectors.filtering() (9) groups players by
 * position keeping only those with at least a given number of goals, so a
 * position whose players all fall short still appears with an empty list
 * rather than being dropped.
 */
class CollectorsFilteringExampleSpec extends Specification {

    def "STAGE-11-AC-02: playersByPositionWithMinGoals keeps only qualifying players per position"() {
        given: "a one-goal threshold"
            def minGoals = 1

        when: "playersByPositionWithMinGoals is called"
            def result = CollectorsFilteringExample.playersByPositionWithMinGoals(minGoals)

        then: "every group contains only players meeting the threshold"
            result.every { position, players -> players.every { it.goals() >= minGoals } }

        and: "the Goalkeeper position is still present with an empty list"
            result.containsKey("Goalkeeper")
            result["Goalkeeper"].isEmpty()
    }

    def "STAGE-11-AC-02: every position from the dataset is present in the result"() {
        given: "every distinct position in the dataset"
            def positions = PremierLeagueDataBase.getAllPlayers().collect { it.position() }.toSet()

        when: "playersByPositionWithMinGoals is called"
            def result = CollectorsFilteringExample.playersByPositionWithMinGoals(1)

        then: "the result's keys match exactly, unlike filtering before grouping"
            result.keySet() == positions
    }
}
