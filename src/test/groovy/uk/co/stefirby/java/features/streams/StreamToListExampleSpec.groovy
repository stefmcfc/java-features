package uk.co.stefirby.java.features.streams

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * Covers STAGE-3-AC-01 — Stream.toList() (16) returns the players' names
 * sorted by goals descending as an unmodifiable list; mutating it throws
 * UnsupportedOperationException.
 */
class StreamToListExampleSpec extends Specification {

    def "STAGE-3-AC-01: topScorerNames returns every player's name sorted by goals descending"() {
        given: "every player's name sorted by goals descending, computed directly"
            def expected = PremierLeagueDataBase.getAllPlayers()
                    .toSorted { a, b -> b.goals() <=> a.goals() }*.name()

        when: "topScorerNames is called"
            def result = StreamToListExample.topScorerNames()

        then: "the result matches the direct sort, led by the top scorer"
            result == expected
            result.first() == "Mohamed Salah"
    }

    def "STAGE-3-AC-01: mutating the returned list throws UnsupportedOperationException"() {
        given: "the result of topScorerNames"
            def result = StreamToListExample.topScorerNames()

        when: "an element is added"
            result.add("Alan Shearer")

        then: "the list rejects the mutation"
            thrown(UnsupportedOperationException)

        when: "an element is removed"
            result.remove(0)

        then: "the list rejects the mutation"
            thrown(UnsupportedOperationException)
    }
}
