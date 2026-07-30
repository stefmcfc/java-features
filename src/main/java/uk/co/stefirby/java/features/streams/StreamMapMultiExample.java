package uk.co.stefirby.java.features.streams;

import java.util.List;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 16: {@code Stream.mapMulti()} — a one-to-many mapping alternative to
 * {@code flatMap} that pushes replacement elements into a consumer instead
 * of allocating an intermediate stream per input element.
 */
public class StreamMapMultiExample {

    /**
     * Flattens each PL team into its players' names via
     * {@code mapMulti}: for every team, each of its players' names is
     * pushed to the downstream consumer.
     *
     * @return the players' names, in team order then player order
     */
    public static List<String> playerNamesFlattenedFromTeams() {
        return PremierLeagueDataBase.getAllTeams().stream()
                .<String>mapMulti((team, downstream) ->
                        PremierLeagueDataBase.getAllPlayers().stream()
                                .filter(player -> player.team().equals(team.name()))
                                .map(Player::name)
                                .forEach(downstream))
                .toList();
    }

    public static void main(String[] args) {
        System.out.println(playerNamesFlattenedFromTeams());
    }
}
