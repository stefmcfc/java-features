package uk.co.stefirby.java.features.optional

import spock.lang.Specification

/**
 * Covers STAGE-5-AC-04 — Optional.stream() (9): flattens several teams'
 * top-scorer lookups into a stream containing only the present players.
 */
class OptionalStreamExampleSpec extends Specification {

    def "STAGE-5-AC-04: topScorersOfTeams drops empties and keeps only present players"() {
        when: "topScorersOfTeams is called with a mix of known and unknown teams"
            def result = OptionalStreamExample.topScorersOfTeams(
                    ["Arsenal", "UnknownFC", "Liverpool", "AnotherUnknownFC"])

        then: "only the two known teams' top scorers are returned, in team order"
            result*.name() == ["Bukayo Saka", "Mohamed Salah"]
    }
}
