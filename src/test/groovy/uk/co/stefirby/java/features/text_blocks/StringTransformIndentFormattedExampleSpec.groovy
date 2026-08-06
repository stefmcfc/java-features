package uk.co.stefirby.java.features.text_blocks

import spock.lang.Specification
import uk.co.stefirby.java.features.data.Team

/**
 * STAGE-12-AC-05: when the String.transform()/indent() (12) and
 * formatted() (15) example builds a report over PL text data, each of the
 * three methods produces its asserted result.
 */
class StringTransformIndentFormattedExampleSpec extends Specification {

    def "STAGE-12-AC-05: transform() upper-cases a club name via the given function"() {
        expect: "transform() applies the given function to the string"
            StringTransformIndentFormattedExample.shoutedName("Arsenal") == "ARSENAL"
    }

    def "STAGE-12-AC-05: indent() indents a stadium line by the given amount"() {
        expect: "indent() prefixes the line with that many spaces and a trailing newline"
            StringTransformIndentFormattedExample.indentedStadiumLine("Emirates Stadium") ==
                    "    Stadium: Emirates Stadium\n"
    }

    def "STAGE-12-AC-05: formatted() fills a club summary template"() {
        given: "a club"
            def team = new Team("Arsenal", "Emirates Stadium", "Mikel Arteta", 1886)

        expect: "formatted() slots the club's name and founding year into the template"
            StringTransformIndentFormattedExample.foundedSummary(team) == "Arsenal, founded 1886"
    }

    def "STAGE-12-AC-05: the combined club report uses all three methods' output"() {
        given: "a club"
            def team = new Team("Arsenal", "Emirates Stadium", "Mikel Arteta", 1886)

        expect: "the report combines the shouted name, indented stadium line, and founded summary"
            StringTransformIndentFormattedExample.clubReport(team) ==
                    "ARSENAL\n    Stadium: Emirates Stadium\n\nArsenal, founded 1886"
    }
}
