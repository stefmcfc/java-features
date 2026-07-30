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
        given:
        def expected = PremierLeagueDataBase.getAllPlayers()
                .toSorted { a, b -> b.goals() <=> a.goals() }*.name()

        when:
        def result = StreamToListExample.topScorerNames()

        then:
        result == expected
        result.first() == "Mohamed Salah"
    }

    def "STAGE-3-AC-01: mutating the returned list throws UnsupportedOperationException"() {
        given:
        def result = StreamToListExample.topScorerNames()

        when:
        result.add("Alan Shearer")

        then:
        thrown(UnsupportedOperationException)

        when:
        result.remove(0)

        then:
        thrown(UnsupportedOperationException)
    }
}
