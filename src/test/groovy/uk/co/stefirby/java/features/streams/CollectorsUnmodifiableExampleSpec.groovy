package uk.co.stefirby.java.features.streams

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * Covers STAGE-11-AC-03 — Collectors.toUnmodifiableSet() /
 * toUnmodifiableMap() (10) collect the distinct nationalities in the
 * league and goals by player name, each rejecting mutation.
 */
class CollectorsUnmodifiableExampleSpec extends Specification {

    def "STAGE-11-AC-03: distinctNationalities collects the distinct nationalities in the league"() {
        given: "the distinct nationalities, computed directly from the dataset"
            def expected = PremierLeagueDataBase.getAllPlayers().collect { it.nationality() }.toSet()

        when: "distinctNationalities is called"
            def result = CollectorsUnmodifiableExample.distinctNationalities()

        then: "the result matches the direct computation"
            result == expected
    }

    def "STAGE-11-AC-03: distinctNationalities rejects mutation"() {
        given: "the distinct-nationalities result"
            def result = CollectorsUnmodifiableExample.distinctNationalities()

        when: "a mutation is attempted"
            result.add("Nowhere")

        then: "the set rejects the mutation"
            thrown(UnsupportedOperationException)
    }

    def "STAGE-11-AC-03: goalsByPlayerName maps every player name to their goals"() {
        given: "goals by player name, computed directly from the dataset"
            def expected = PremierLeagueDataBase.getAllPlayers().collectEntries { [it.name(), it.goals()] }

        when: "goalsByPlayerName is called"
            def result = CollectorsUnmodifiableExample.goalsByPlayerName()

        then: "the result matches the direct computation"
            result == expected
    }

    def "STAGE-11-AC-03: goalsByPlayerName rejects mutation"() {
        given: "the goals-by-player-name result"
            def result = CollectorsUnmodifiableExample.goalsByPlayerName()

        when: "a mutation is attempted"
            result.put("Nobody", 0)

        then: "the map rejects the mutation"
            thrown(UnsupportedOperationException)
    }
}
