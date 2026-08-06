package uk.co.stefirby.java.features.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 10: {@code List.copyOf()} / {@code Map.copyOf()} — defensive copy
 * factories that snapshot a mutable collection into an unmodifiable one;
 * later mutation of the source has no effect on the copy.
 */
public class ImmutableCopyExample {

    /**
     * @return Arsenal's squad as a freshly built, mutable list
     */
    public static List<Player> mutableSquad() {
        return new ArrayList<>(PremierLeagueDataBase.getAllPlayers().stream()
                .filter(player -> player.team().equals("Arsenal"))
                .toList());
    }

    /**
     * @param squad a mutable squad list
     * @return an unmodifiable snapshot of {@code squad} at the time of the
     *         call
     */
    public static List<Player> squadCopy(List<Player> squad) {
        return List.copyOf(squad);
    }

    /**
     * @return every player's name mapped to their goals, as a freshly
     *         built, mutable map
     */
    public static Map<String, Integer> mutableGoalsByName() {
        Map<String, Integer> goalsByName = new HashMap<>();
        PremierLeagueDataBase.getAllPlayers().forEach(player -> goalsByName.put(player.name(), player.goals()));
        return goalsByName;
    }

    /**
     * @param goalsByName a mutable name-to-goals map
     * @return an unmodifiable snapshot of {@code goalsByName} at the time
     *         of the call
     */
    public static Map<String, Integer> goalsByNameCopy(Map<String, Integer> goalsByName) {
        return Map.copyOf(goalsByName);
    }

    public static void main(String[] args) {
        List<Player> squad = mutableSquad();
        List<Player> squadCopy = squadCopy(squad);
        squad.clear();
        System.out.println("Copy survives source clear: " + (squadCopy.size()));

        Map<String, Integer> goalsByName = mutableGoalsByName();
        Map<String, Integer> goalsByNameCopy = goalsByNameCopy(goalsByName);
        goalsByName.clear();
        System.out.println("Copy survives source clear: " + (goalsByNameCopy.size()));
    }
}
