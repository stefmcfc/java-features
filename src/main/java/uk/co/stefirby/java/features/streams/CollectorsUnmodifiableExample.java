package uk.co.stefirby.java.features.streams;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 10: {@code Collectors.toUnmodifiableSet()} /
 * {@code Collectors.toUnmodifiableMap()} — terminal collectors whose
 * results reject mutation with {@link UnsupportedOperationException},
 * without needing a separate {@code Collections.unmodifiable*} wrapping
 * step.
 */
public class CollectorsUnmodifiableExample {

    /**
     * @return the distinct nationalities represented in the league, as an
     *         unmodifiable set
     */
    public static Set<String> distinctNationalities() {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .map(Player::nationality)
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * @return every player's name mapped to their goals, as an
     *         unmodifiable map
     */
    public static Map<String, Integer> goalsByPlayerName() {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .collect(Collectors.toUnmodifiableMap(Player::name, Player::goals));
    }

    public static void main(String[] args) {
        System.out.println("Nationalities:    " + distinctNationalities());
        System.out.println("Goals by name:    " + goalsByPlayerName());
    }
}
