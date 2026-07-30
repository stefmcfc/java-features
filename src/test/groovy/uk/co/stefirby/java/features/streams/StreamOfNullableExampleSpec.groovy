package uk.co.stefirby.java.features.streams

import spock.lang.Specification

/**
 * Covers STAGE-3-AC-06 — Stream.ofNullable() (9): a null lookup result
 * yields an empty stream; a non-null player yields exactly that one
 * element.
 */
class StreamOfNullableExampleSpec extends Specification {

    def "STAGE-3-AC-06: an unknown player name yields an empty stream"() {
        expect:
        StreamOfNullableExample.lookupAsStream("Alan Shearer").toList().isEmpty()
    }

    def "STAGE-3-AC-06: a known player name yields exactly that one player"() {
        when:
        def result = StreamOfNullableExample.lookupAsStream("Mohamed Salah").toList()

        then:
        result.size() == 1
        result.first().name() == "Mohamed Salah"
    }
}
