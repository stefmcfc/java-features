package uk.co.stefirby.java.features.streams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 10: {@code Collectors.toUnmodifiableList()} — here as the
 * downstream of {@code Collectors.groupingBy}, so each group comes out as
 * an unmodifiable list. This is the logic stage 4's
 * {@code GET /api/streams/grouped-by-team} endpoint delegates to.
 */
public class CollectorsGroupingByExample {

    /**
     * @return all PL players grouped by team name; each team's players are
     *         an unmodifiable list — mutating one throws
     *         {@link UnsupportedOperationException}
     */
    public static Map<String, List<Player>> playersGroupedByTeam() {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .collect(Collectors.groupingBy(
                        Player::team,
                        Collectors.toUnmodifiableList()));
    }

    public static void main(String[] args) {
        playersGroupedByTeam().forEach((team, players) ->
                System.out.println(team + ": " + players.stream().map(Player::name).toList()));
    }
}
