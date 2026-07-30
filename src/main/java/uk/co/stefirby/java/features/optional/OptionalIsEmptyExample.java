package uk.co.stefirby.java.features.optional;

import java.util.Comparator;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 11: {@code Optional.isEmpty()} — a direct emptiness check, avoiding
 * the double-negative of {@code !isPresent()}.
 */
public class OptionalIsEmptyExample {

    /**
     * @param teamName the team to look up
     * @return {@code true} if the team has no top scorer (no players, or an
     *         unknown team name); {@code false} otherwise
     */
    public static boolean teamHasNoTopScorer(String teamName) {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .filter(player -> player.team().equals(teamName))
                .max(Comparator.comparingInt(Player::goals))
                .isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(teamHasNoTopScorer("Arsenal"));
        System.out.println(teamHasNoTopScorer("UnknownFC"));
    }
}
