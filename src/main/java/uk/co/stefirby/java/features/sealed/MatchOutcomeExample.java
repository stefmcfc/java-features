package uk.co.stefirby.java.features.sealed;

import java.util.List;
import uk.co.stefirby.java.features.data.Match;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 17: sealed interfaces restrict a hierarchy to a fixed, compiler-known
 * set of subtypes — {@link MatchOutcome} permits exactly {@link HomeWin},
 * {@link AwayWin} and {@link Draw}, so pattern-matching {@code switch} over
 * the hierarchy can be exhaustive without a {@code default} branch (see
 * {@code SwitchPatternMatchingExample} in the {@code pattern_matching}
 * package).
 */
public class MatchOutcomeExample {

    /**
     * The result of a completed match — sealed so the compiler knows the
     * three shapes below are the only possible outcomes.
     */
    public sealed interface MatchOutcome permits HomeWin, AwayWin, Draw {
    }

    /**
     * The home side scored more goals.
     *
     * @param homeTeam  the winning home club's name
     * @param awayTeam  the beaten away club's name
     * @param homeGoals goals scored by the home team
     * @param awayGoals goals scored by the away team
     */
    public record HomeWin(String homeTeam, String awayTeam, int homeGoals, int awayGoals)
            implements MatchOutcome {
    }

    /**
     * The away side scored more goals.
     *
     * @param homeTeam  the beaten home club's name
     * @param awayTeam  the winning away club's name
     * @param homeGoals goals scored by the home team
     * @param awayGoals goals scored by the away team
     */
    public record AwayWin(String homeTeam, String awayTeam, int homeGoals, int awayGoals)
            implements MatchOutcome {
    }

    /**
     * Both sides scored the same number of goals.
     *
     * @param homeTeam  the home club's name
     * @param awayTeam  the away club's name
     * @param goalsEach the number of goals each side scored
     */
    public record Draw(String homeTeam, String awayTeam, int goalsEach)
            implements MatchOutcome {
    }

    /**
     * @param match a completed match
     * @return the match's result as exactly one of the sealed outcomes
     */
    public static MatchOutcome outcomeOf(Match match) {
        if (match.homeGoals() > match.awayGoals()) {
            return new HomeWin(match.homeTeam(), match.awayTeam(), match.homeGoals(), match.awayGoals());
        }
        if (match.awayGoals() > match.homeGoals()) {
            return new AwayWin(match.homeTeam(), match.awayTeam(), match.homeGoals(), match.awayGoals());
        }
        return new Draw(match.homeTeam(), match.awayTeam(), match.homeGoals());
    }

    /**
     * @return every dataset match's result, one outcome per match
     */
    public static List<MatchOutcome> classifyAll() {
        return PremierLeagueDataBase.getAllMatches().stream()
                .map(MatchOutcomeExample::outcomeOf)
                .toList();
    }

    public static void main(String[] args) {
        classifyAll().forEach(System.out::println);
    }
}
