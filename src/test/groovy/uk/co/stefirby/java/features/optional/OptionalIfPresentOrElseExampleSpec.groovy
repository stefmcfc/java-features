package uk.co.stefirby.java.features.optional

import spock.lang.Specification

/**
 * Covers STAGE-5-AC-02 — Optional.ifPresentOrElse() (9): a present player
 * runs the present action; an absent lookup runs the empty action instead.
 */
class OptionalIfPresentOrElseExampleSpec extends Specification {

    def "STAGE-5-AC-02: a known player runs the present action"() {
        when: "describePlayerLookup is called with a known player's name"
            def result = OptionalIfPresentOrElseExample.describePlayerLookup("Mohamed Salah")

        then: "the description names the player and their team"
            result.contains("Mohamed Salah")
            result.contains("Liverpool")
    }

    def "STAGE-5-AC-02: an unknown player runs the empty action"() {
        when: "describePlayerLookup is called with an unknown player's name"
            def result = OptionalIfPresentOrElseExample.describePlayerLookup("Alan Shearer")

        then: "the description reports no such player"
            result.contains("No player named Alan Shearer")
    }
}
