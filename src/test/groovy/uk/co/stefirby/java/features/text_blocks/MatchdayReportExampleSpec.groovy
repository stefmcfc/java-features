package uk.co.stefirby.java.features.text_blocks

import spock.lang.Specification
import uk.co.stefirby.java.features.data.Match

import java.time.LocalDate

/**
 * STAGE-7-AC-02: the text-blocks (15) example's multi-line matchday report
 * shall produce exactly the expected line structure and content.
 */
class MatchdayReportExampleSpec extends Specification {

    def "STAGE-7-AC-02: matchdayReport builds exactly the expected line structure and content"() {
        given: "a known match"
            def match = new Match("Liverpool", "Tottenham Hotspur", 3, 1, LocalDate.of(2025, 8, 23))

        when: "the matchday report is built"
            def report = MatchdayReportExample.matchdayReport(match)

        then: "the report has exactly the expected lines"
            report == """Matchday Report
===============
Liverpool vs Tottenham Hotspur
Score: 3 - 1
Date: 2025-08-23
"""
    }
}
