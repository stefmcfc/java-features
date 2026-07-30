package uk.co.stefirby.java.features.collections

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * Covers STAGE-6-AC-02 — Sequenced Collections (21): on the season's
 * matches ordered by date, getFirst() returns the first element, getLast()
 * the last, and reversed() a view in opposite order.
 */
class SequencedCollectionsExampleSpec extends Specification {

    def "STAGE-6-AC-02: getFirst returns the first element of the ordered match list"() {
        given: "the season's matches ordered by date, computed directly"
            def ordered = PremierLeagueDataBase.getAllMatches().toSorted { it.date() }

        when: "the Sequenced Collections example asks for the opening match"
            def result = SequencedCollectionsExample.openingMatch()

        then: "the first element is returned"
            result == ordered.first()
    }

    def "STAGE-6-AC-02: getLast returns the last element of the ordered match list"() {
        given: "the season's matches ordered by date, computed directly"
            def ordered = PremierLeagueDataBase.getAllMatches().toSorted { it.date() }

        when: "the Sequenced Collections example asks for the latest match"
            def result = SequencedCollectionsExample.latestMatch()

        then: "the last element is returned"
            result == ordered.last()
    }

    def "STAGE-6-AC-02: reversed returns a view of the ordered match list in opposite order"() {
        given: "the season's matches ordered by date, computed directly"
            def ordered = PremierLeagueDataBase.getAllMatches().toSorted { it.date() }

        when: "the Sequenced Collections example asks for the matches latest-first"
            def result = SequencedCollectionsExample.matchesLatestFirst()

        then: "the view holds the same matches in opposite order"
            result == ordered.reverse()
    }
}
