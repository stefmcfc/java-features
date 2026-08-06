package uk.co.stefirby.java.features.streams

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * Covers STAGE-11-AC-05 — Predicate.not() (11) filters the dataset to
 * outfield players, matching the equivalent negated-lambda filter.
 */
class PredicateNotExampleSpec extends Specification {

    def "STAGE-11-AC-05: outfieldPlayers returns exactly the players whose position is not Goalkeeper"() {
        given: "the players filtered by an equivalent negated-lambda goalkeeper test"
            def expected = PremierLeagueDataBase.getAllPlayers().findAll { !(it.position() == "Goalkeeper") }

        when: "outfieldPlayers is called"
            def result = PredicateNotExample.outfieldPlayers()

        then: "the result matches the negated-lambda filter"
            result == expected

        and: "no returned player is a Goalkeeper"
            result.every { it.position() != "Goalkeeper" }
    }
}
