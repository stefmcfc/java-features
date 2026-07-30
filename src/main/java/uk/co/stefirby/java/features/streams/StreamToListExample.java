package uk.co.stefirby.java.features.streams;

import java.util.Comparator;
import java.util.List;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 16: {@code Stream.toList()} — a shorthand terminal operation that
 * collects the stream into an <em>unmodifiable</em> list, replacing the
 * wordier {@code collect(Collectors.toList())} (which returns a modifiable
 * list with no such guarantee).
 */
public class StreamToListExample {

    /**
     * @return every player's name sorted by goals descending, as an
     *         unmodifiable list — mutating it throws
     *         {@link UnsupportedOperationException}
     */
    public static List<String> topScorerNames() {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .sorted(Comparator.comparingInt(Player::goals).reversed())
                .map(Player::name)
                .toList();
    }

    public static void main(String[] args) {
        System.out.println(topScorerNames());
    }
}
