package uk.co.stefirby.java.features.pattern_matching;

import uk.co.stefirby.java.features.data.Match;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;
import uk.co.stefirby.java.features.data.Team;

/**
 * Java 16: pattern matching for {@code instanceof} — the type test binds a
 * pattern variable in one step, so classifying an object that may be a
 * {@link Player}, {@link Team} or {@link Match} needs no explicit casts.
 */
public class InstanceofPatternMatchingExample {

    /**
     * @param subject any object, expected to be a {@link Player},
     *                {@link Team} or {@link Match}
     * @return a one-line description built from the matched type's
     *         components, or a fallback for anything else
     */
    public static String describe(Object subject) {
        if (subject instanceof Player player) {
            return "Player " + player.name() + " plays " + player.position() + " for " + player.team();
        }
        if (subject instanceof Team team) {
            return "Team " + team.name() + " play at " + team.stadium() + " under " + team.manager();
        }
        if (subject instanceof Match match) {
            return "Match " + match.homeTeam() + " " + match.homeGoals() + "-" + match.awayGoals()
                    + " " + match.awayTeam() + " on " + match.date();
        }
        return "Unrecognised object: " + subject;
    }

    public static void main(String[] args) {
        System.out.println(describe(PremierLeagueDataBase.getAllPlayers().getFirst()));
        System.out.println(describe(PremierLeagueDataBase.getAllTeams().getFirst()));
        System.out.println(describe(PremierLeagueDataBase.getAllMatches().getFirst()));
        System.out.println(describe(42));
    }
}
