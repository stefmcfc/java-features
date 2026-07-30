package uk.co.stefirby.java.features.optional

import spock.lang.Specification

/**
 * Covers STAGE-5-AC-03 — Optional.isEmpty() (11): reports true for a team
 * with no players and false for a populated team.
 */
class OptionalIsEmptyExampleSpec extends Specification {

    def "STAGE-5-AC-03: a team with no players reports true"() {
        expect: "teamHasNoTopScorer is true for an unknown team"
            OptionalIsEmptyExample.teamHasNoTopScorer("UnknownFC")
    }

    def "STAGE-5-AC-03: a populated team reports false"() {
        expect: "teamHasNoTopScorer is false for a known team"
            !OptionalIsEmptyExample.teamHasNoTopScorer("Arsenal")
    }
}
