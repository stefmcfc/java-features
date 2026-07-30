package uk.co.stefirby.java.features.optional;

import java.util.Comparator;
import java.util.Optional;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 9: {@code Optional.or()} — supplies a fallback {@code Optional}
 * lazily when the original is empty, without unwrapping to a nullable value
 * first.
 */
public class OptionalOrExample {

    /**
     * @param teamName the team to look up
     * @return that team's top scorer, or {@link Optional#empty()} if the
     *         team has no players (including an unknown team name)
     */
    public static Optional<Player> topScorerOfTeam(String teamName) {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .filter(player -> player.team().equals(teamName))
                .max(Comparator.comparingInt(Player::goals));
    }

    /**
     * @return the top scorer across the whole league
     */
    public static Optional<Player> leagueTopScorer() {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .max(Comparator.comparingInt(Player::goals));
    }

    /**
     * @param teamName the team to look up
     * @return that team's top scorer, falling back to {@link #leagueTopScorer()}
     *         if the team has no top scorer of its own
     */
    public static Optional<Player> topScorerOrLeagueTop(String teamName) {
        return topScorerOfTeam(teamName).or(OptionalOrExample::leagueTopScorer);
    }

    public static void main(String[] args) {
        System.out.println(topScorerOrLeagueTop("UnknownFC"));
    }
}
