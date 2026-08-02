package uk.co.stefirby.java.features.sealed

import spock.lang.Specification
import uk.co.stefirby.java.features.data.Match

import java.time.LocalDate

/**
 * STAGE-8-AC-01: the sealed types (17) example models match outcomes as a
 * sealed hierarchy permitting exactly a fixed set of subtypes (HomeWin,
 * AwayWin, Draw), asserted via the permitted-subclasses reflection API.
 */
class MatchOutcomeExampleSpec extends Specification {

    def "STAGE-8-AC-01: MatchOutcome is a sealed hierarchy"() {
        expect: "MatchOutcome is declared sealed"
            MatchOutcomeExample.MatchOutcome.isSealed()
    }

    def "STAGE-8-AC-01: MatchOutcome permits exactly HomeWin, AwayWin and Draw"() {
        expect: "the permitted subtypes are exactly the fixed set"
            MatchOutcomeExample.MatchOutcome.permittedSubclasses.toList() as Set == [
                    MatchOutcomeExample.HomeWin,
                    MatchOutcomeExample.AwayWin,
                    MatchOutcomeExample.Draw
            ] as Set
    }

    def "STAGE-8-AC-01: outcomeOf classifies a home victory as HomeWin"() {
        given: "a match the home side won"
            def match = new Match("Arsenal", "Tottenham Hotspur", 3, 0, LocalDate.of(2026, 2, 7))

        when: "the match is classified"
            def outcome = MatchOutcomeExample.outcomeOf(match)

        then: "the outcome is a HomeWin carrying the fixture and score"
            outcome instanceof MatchOutcomeExample.HomeWin
            outcome.homeTeam() == "Arsenal"
            outcome.awayTeam() == "Tottenham Hotspur"
            outcome.homeGoals() == 3
            outcome.awayGoals() == 0
    }

    def "STAGE-8-AC-01: outcomeOf classifies an away victory as AwayWin"() {
        given: "a match the away side won"
            def match = new Match("Manchester United", "Arsenal", 0, 2, LocalDate.of(2025, 8, 16))

        when: "the match is classified"
            def outcome = MatchOutcomeExample.outcomeOf(match)

        then: "the outcome is an AwayWin carrying the fixture and score"
            outcome instanceof MatchOutcomeExample.AwayWin
            outcome.homeTeam() == "Manchester United"
            outcome.awayTeam() == "Arsenal"
            outcome.homeGoals() == 0
            outcome.awayGoals() == 2
    }

    def "STAGE-8-AC-01: outcomeOf classifies a level score as Draw"() {
        given: "a match that finished level"
            def match = new Match("Arsenal", "Liverpool", 2, 2, LocalDate.of(2025, 10, 4))

        when: "the match is classified"
            def outcome = MatchOutcomeExample.outcomeOf(match)

        then: "the outcome is a Draw carrying the fixture and the shared score"
            outcome instanceof MatchOutcomeExample.Draw
            outcome.homeTeam() == "Arsenal"
            outcome.awayTeam() == "Liverpool"
            outcome.goalsEach() == 2
    }

    def "STAGE-8-AC-01: outcomeOf covers every match in the dataset"() {
        when: "every dataset match is classified"
            def outcomes = MatchOutcomeExample.classifyAll()

        then: "each match yields exactly one MatchOutcome"
            outcomes.size() == uk.co.stefirby.java.features.data.PremierLeagueDataBase.getAllMatches().size()
            outcomes.every { it instanceof MatchOutcomeExample.MatchOutcome }
    }
}
