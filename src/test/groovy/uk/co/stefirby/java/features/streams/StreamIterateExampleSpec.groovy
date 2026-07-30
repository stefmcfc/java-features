package uk.co.stefirby.java.features.streams

import spock.lang.Specification

/**
 * Covers STAGE-3-AC-05 — the Stream.iterate() predicate overload (9)
 * generates the matchweek numbers 1 through 38 without an explicit
 * limit() call.
 */
class StreamIterateExampleSpec extends Specification {

    def "STAGE-3-AC-05: matchweekNumbers generates 1 through 38"() {
        when:
        def result = StreamIterateExample.matchweekNumbers()

        then:
        result == (1..38).toList()
    }
}
