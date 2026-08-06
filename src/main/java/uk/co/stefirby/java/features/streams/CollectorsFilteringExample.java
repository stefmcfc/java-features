package uk.co.stefirby.java.features.streams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 9: {@code Collectors.filtering()} — a downstream collector that
 * filters within each group, here as the downstream of
 * {@code Collectors.groupingBy}. Unlike filtering the stream before
 * grouping, a position whose players all fall short of the threshold still
 * appears in the result, with an empty list rather than being dropped.
 */
public class CollectorsFilteringExample {

    /**
     * @param minGoals the minimum goals a player must have scored to
     *                 qualify
     * @return every position mapped to its qualifying players; positions
     *         with no qualifying players still appear, with an empty list
     */
    public static Map<String, List<Player>> playersByPositionWithMinGoals(int minGoals) {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .collect(Collectors.groupingBy(
                        Player::position,
                        Collectors.filtering(
                                player -> player.goals() >= minGoals,
                                Collectors.toList())));
    }

    public static void main(String[] args) {
        playersByPositionWithMinGoals(10).forEach((position, players) ->
                System.out.println(position + ": " + players.stream().map(Player::name).toList()));
    }
}
