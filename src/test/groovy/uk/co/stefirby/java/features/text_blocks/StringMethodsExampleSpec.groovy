package uk.co.stefirby.java.features.text_blocks

import spock.lang.Specification
import uk.co.stefirby.java.features.data.Match

import java.time.LocalDate

/**
 * STAGE-10-AC-02: when the String methods (11) example runs over PL text
 * data, isBlank(), strip(), repeat(), and lines() shall each produce their
 * asserted result.
 */
class StringMethodsExampleSpec extends Specification {

    def "STAGE-10-AC-02: isBlank() flags an all-whitespace team-sheet line but not a player line"() {
        expect: "isBlank() reports the all-whitespace line as blank"
            StringMethodsExample.isBlankLine(" \t   ")

        and: "a line naming a player is not blank"
            !StringMethodsExample.isBlankLine("  Erling Haaland  ")
    }

    def "STAGE-10-AC-02: strip() removes Unicode whitespace padding around a player name"() {
        expect: "strip() removes padding trim() would leave behind (U+2005 is beyond trim's U+0020 cutoff)"
            StringMethodsExample.strippedName("  Erling Haaland  ") == "Erling Haaland"
    }

    def "STAGE-10-AC-02: repeat() underlines a report heading to its exact width"() {
        expect: "repeat() produces an underline exactly as wide as the heading"
            StringMethodsExample.headingUnderline("Top Scorers") == "==========="
    }

    def "STAGE-10-AC-02: lines() splits a matchday report into its individual lines"() {
        given: "a matchday report for a known match"
            def match = new Match("Liverpool", "Tottenham Hotspur", 3, 1, LocalDate.of(2025, 8, 23))
            def report = MatchdayReportExample.matchdayReport(match)

        when: "the String methods example splits the report with lines()"
            def lines = StringMethodsExample.reportLines(report)

        then: "each report line comes back as its own element, without terminators"
            lines == [
                    "Matchday Report",
                    "===============",
                    "Liverpool vs Tottenham Hotspur",
                    "Score: 3 - 1",
                    "Date: 2025-08-23",
            ]
    }
}
