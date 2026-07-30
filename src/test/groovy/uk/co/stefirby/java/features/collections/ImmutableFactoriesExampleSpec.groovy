package uk.co.stefirby.java.features.collections

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * Covers STAGE-6-AC-01a and STAGE-6-AC-01b — the immutable factory methods
 * List.of(), Set.of(), and Map.of() (9) build collections over PL data, and
 * every collection they return rejects mutation.
 */
class ImmutableFactoriesExampleSpec extends Specification {

    def "STAGE-6-AC-01a: topThreeScorers builds a List.of() of the league's top three scorers"() {
        given: "the top three scorers by goals, computed directly from the dataset"
            def expected = PremierLeagueDataBase.getAllPlayers()
                    .toSorted { -it.goals() }
                    .take(3)

        when: "the immutable-factories example is invoked"
            def result = ImmutableFactoriesExample.topThreeScorers()

        then: "the list holds the top three scorers in goal order"
            result == expected
    }

    def "STAGE-6-AC-01a: topThreeScorerNationalities builds a Set.of() of those scorers' nationalities"() {
        given: "the nationalities of the top three scorers, computed directly"
            def expected = PremierLeagueDataBase.getAllPlayers()
                    .toSorted { -it.goals() }
                    .take(3)
                    .collect { it.nationality() } as Set

        when: "the immutable-factories example is invoked"
            def result = ImmutableFactoriesExample.topThreeScorerNationalities()

        then: "the set holds exactly those nationalities"
            result == expected
    }

    def "STAGE-6-AC-01a: goalsByTopScorer builds a Map.of() from scorer name to goals"() {
        given: "the top three scorers' names mapped to their goals, computed directly"
            def expected = PremierLeagueDataBase.getAllPlayers()
                    .toSorted { -it.goals() }
                    .take(3)
                    .collectEntries { [it.name(), it.goals()] }

        when: "the immutable-factories example is invoked"
            def result = ImmutableFactoriesExample.goalsByTopScorer()

        then: "the map holds exactly those name-to-goals entries"
            result == expected
    }

    def "STAGE-6-AC-01b: mutating the #collection collection throws UnsupportedOperationException"() {
        when: "a mutation is attempted on the collection"
            mutation.run()

        then: "UnsupportedOperationException is thrown"
            thrown(UnsupportedOperationException)

        where:
            collection | mutation
            "List"     | { ImmutableFactoriesExample.topThreeScorers().clear() }
            "Set"      | { ImmutableFactoriesExample.topThreeScorerNationalities().add("Scotland") }
            "Map"      | { ImmutableFactoriesExample.goalsByTopScorer().put("Nobody", 0) }
    }
}
