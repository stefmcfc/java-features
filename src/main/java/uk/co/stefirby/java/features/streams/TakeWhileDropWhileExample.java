package uk.co.stefirby.java.features.streams;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 9: {@code Stream.takeWhile()} / {@code Stream.dropWhile()} — on an
 * ordered stream, {@code takeWhile} keeps elements until the predicate
 * first fails and {@code dropWhile} discards exactly that prefix, so the
 * two partition the stream at the same cut point.
 */
public class TakeWhileDropWhileExample {

    private static Stream<Player> playersByGoalsDescending() {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .sorted(Comparator.comparingInt(Player::goals).reversed());
    }

    /**
     * @param goalsThreshold the goals count a player must exceed
     * @return the leading players (sorted by goals descending) scoring
     *         strictly more than the threshold
     */
    public static List<Player> leadingScorersAbove(int goalsThreshold) {
        return playersByGoalsDescending()
                .takeWhile(player -> player.goals() > goalsThreshold)
                .toList();
    }

    /**
     * @param goalsThreshold the goals count the leaders must exceed
     * @return exactly the players {@link #leadingScorersAbove(int)} leaves
     *         out, in the same goals-descending order
     */
    public static List<Player> remainingScorers(int goalsThreshold) {
        return playersByGoalsDescending()
                .dropWhile(player -> player.goals() > goalsThreshold)
                .toList();
    }

    public static void main(String[] args) {
        System.out.println("Leaders above 10 goals: " + leadingScorersAbove(10));
        System.out.println("Remainder:              " + remainingScorers(10));
    }
}
