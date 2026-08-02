package uk.co.stefirby.java.features.pattern_matching

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase
import uk.co.stefirby.java.features.sealed.MatchOutcomeExample

/**
 * STAGE-8-AC-03: when the switch pattern-matching (21) example is given a
 * match outcome, it produces a summary via record deconstruction patterns,
 * covering all sealed cases without a default branch.
 */
class SwitchPatternMatchingExampleSpec extends Specification {

    def "STAGE-8-AC-03: summarise deconstructs a HomeWin into a summary"() {
        given: "a HomeWin outcome"
            def outcome = new MatchOutcomeExample.HomeWin("Arsenal", "Tottenham Hotspur", 3, 0)

        when: "the outcome is summarised"
            def summary = SwitchPatternMatchingExample.summarise(outcome)

        then: "the summary is built from the deconstructed components"
            summary == "Arsenal beat Tottenham Hotspur 3-0 at home"
    }

    def "STAGE-8-AC-03: summarise deconstructs an AwayWin into a summary"() {
        given: "an AwayWin outcome"
            def outcome = new MatchOutcomeExample.AwayWin("Manchester United", "Arsenal", 0, 2)

        when: "the outcome is summarised"
            def summary = SwitchPatternMatchingExample.summarise(outcome)

        then: "the summary is built from the deconstructed components"
            summary == "Arsenal won 2-0 away at Manchester United"
    }

    def "STAGE-8-AC-03: summarise deconstructs a Draw into a summary"() {
        given: "a Draw outcome"
            def outcome = new MatchOutcomeExample.Draw("Arsenal", "Liverpool", 2)

        when: "the outcome is summarised"
            def summary = SwitchPatternMatchingExample.summarise(outcome)

        then: "the summary is built from the deconstructed components"
            summary == "Arsenal and Liverpool drew 2-2"
    }

    def "STAGE-8-AC-03: summariseAll covers every sealed case across the dataset"() {
        when: "every dataset match's outcome is summarised"
            def summaries = SwitchPatternMatchingExample.summariseAll()

        then: "each match yields exactly one non-empty summary"
            summaries.size() == PremierLeagueDataBase.getAllMatches().size()
            summaries.every { it instanceof String && !it.isEmpty() }
    }
}
