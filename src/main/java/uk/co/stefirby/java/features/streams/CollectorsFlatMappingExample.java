package uk.co.stefirby.java.features.streams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 9: {@code Collectors.flatMapping()} — a downstream collector that
 * flat-maps each element to a stream before collecting, here as the
 * downstream of {@code Collectors.groupingBy}, so each team maps straight
 * to its goal-scorers' names without an intermediate per-team list of
 * players.
 */
public class CollectorsFlatMappingExample {

    /**
     * @return every team name mapped to the names of that team's
     *         goal-scorers (players with at least one goal)
     */
    public static Map<String, List<String>> scorerNamesByTeam() {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .collect(Collectors.groupingBy(
                        Player::team,
                        Collectors.flatMapping(
                                player -> player.goals() > 0 ? Stream.of(player.name()) : Stream.empty(),
                                Collectors.toList())));
    }

    public static void main(String[] args) {
        scorerNamesByTeam().forEach((team, scorers) -> System.out.println(team + ": " + scorers));
    }
}
