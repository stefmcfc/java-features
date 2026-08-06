package uk.co.stefirby.java.features.streams

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * Covers STAGE-11-AC-01 — Collectors.flatMapping() (9) groups the dataset
 * by team with a flat-mapping downstream collector, mapping each team name
 * to the names of that team's goal-scorers (players with at least one
 * goal).
 */
class CollectorsFlatMappingExampleSpec extends Specification {

    def "STAGE-11-AC-01: scorerNamesByTeam maps each team to its goal-scorers' names"() {
        given: "the expected scorer names per team, computed directly from the dataset"
            def expected = PremierLeagueDataBase.getAllPlayers()
                    .findAll { it.goals() > 0 }
                    .groupBy { it.team() }
                    .collectEntries { team, players -> [team, players.collect { it.name() }] }

        when: "scorerNamesByTeam is called"
            def result = CollectorsFlatMappingExample.scorerNamesByTeam()

        then: "the result matches the direct computation"
            result == expected
    }

    def "STAGE-11-AC-01: Manchester City's entry excludes Ederson"() {
        when: "scorerNamesByTeam is called"
            def result = CollectorsFlatMappingExample.scorerNamesByTeam()

        then: "Ederson, a goalless goalkeeper, is not among Manchester City's scorers"
            !result["Manchester City"].contains("Ederson")
    }
}
