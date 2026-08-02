package uk.co.stefirby.java.features.pattern_matching;

import java.util.List;
import uk.co.stefirby.java.features.sealed.MatchOutcomeExample;
import uk.co.stefirby.java.features.sealed.MatchOutcomeExample.AwayWin;
import uk.co.stefirby.java.features.sealed.MatchOutcomeExample.Draw;
import uk.co.stefirby.java.features.sealed.MatchOutcomeExample.HomeWin;
import uk.co.stefirby.java.features.sealed.MatchOutcomeExample.MatchOutcome;

/**
 * Java 21: pattern matching for {@code switch} with record deconstruction
 * patterns — each case matches one subtype of the sealed
 * {@link MatchOutcome} hierarchy and binds its components directly. Because
 * the hierarchy is sealed, the switch is exhaustive with no {@code default}
 * branch: adding a new outcome subtype becomes a compile error here until a
 * case covers it.
 */
public class SwitchPatternMatchingExample {

    /**
     * @param outcome a match outcome from the sealed hierarchy
     * @return a one-line summary built from the outcome's deconstructed
     *         components
     */
    public static String summarise(MatchOutcome outcome) {
        return switch (outcome) {
            case HomeWin(String homeTeam, String awayTeam, int homeGoals, int awayGoals) ->
                    homeTeam + " beat " + awayTeam + " " + homeGoals + "-" + awayGoals + " at home";
            case AwayWin(String homeTeam, String awayTeam, int homeGoals, int awayGoals) ->
                    awayTeam + " won " + awayGoals + "-" + homeGoals + " away at " + homeTeam;
            case Draw(String homeTeam, String awayTeam, int goalsEach) ->
                    homeTeam + " and " + awayTeam + " drew " + goalsEach + "-" + goalsEach;
        };
    }

    /**
     * @return a summary line for every match in the dataset
     */
    public static List<String> summariseAll() {
        return MatchOutcomeExample.classifyAll().stream()
                .map(SwitchPatternMatchingExample::summarise)
                .toList();
    }

    public static void main(String[] args) {
        summariseAll().forEach(System.out::println);
    }
}
