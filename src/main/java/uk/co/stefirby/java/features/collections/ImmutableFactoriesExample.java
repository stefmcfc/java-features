package uk.co.stefirby.java.features.collections;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 9: immutable collection factory methods {@code List.of()},
 * {@code Set.of()}, and {@code Map.of()} — concise literals for small
 * collections whose returned instances are structurally immutable: any
 * mutation attempt throws {@link UnsupportedOperationException}.
 */
public class ImmutableFactoriesExample {

    private static List<Player> topThree() {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .sorted(Comparator.comparingInt(Player::goals).reversed())
                .limit(3)
                .toList();
    }

    /**
     * @return the league's top three scorers in goal order, as a
     *         {@code List.of()} list
     */
    public static List<Player> topThreeScorers() {
        List<Player> topThree = topThree();
        return List.of(topThree.get(0), topThree.get(1), topThree.get(2));
    }

    /**
     * @return the nationalities of the league's top three scorers, as a
     *         {@code Set.of()} set
     */
    public static Set<String> topThreeScorerNationalities() {
        List<Player> topThree = topThree();
        return Set.of(
                topThree.get(0).nationality(),
                topThree.get(1).nationality(),
                topThree.get(2).nationality());
    }

    /**
     * @return the league's top three scorers' names mapped to their goal
     *         tallies, as a {@code Map.of()} map
     */
    public static Map<String, Integer> goalsByTopScorer() {
        List<Player> topThree = topThree();
        return Map.of(
                topThree.get(0).name(), topThree.get(0).goals(),
                topThree.get(1).name(), topThree.get(1).goals(),
                topThree.get(2).name(), topThree.get(2).goals());
    }

    public static void main(String[] args) {
        System.out.println("Top three scorers:      " + topThreeScorers());
        System.out.println("Their nationalities:    " + topThreeScorerNationalities());
        System.out.println("Goals by top scorer:    " + goalsByTopScorer());
    }
}
