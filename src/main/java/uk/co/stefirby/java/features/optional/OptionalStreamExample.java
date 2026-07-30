package uk.co.stefirby.java.features.optional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 9: {@code Optional.stream()} — turns an {@code Optional} into a
 * zero-or-one-element stream, so a list of lookups can be flattened straight
 * into their present values with {@code flatMap}, dropping the empties.
 */
public class OptionalStreamExample {

    /**
     * @param teamNames the teams to look up, in order
     * @return each named team's top scorer, in the same order, with unknown
     *         or player-less teams dropped
     */
    public static List<Player> topScorersOfTeams(List<String> teamNames) {
        return teamNames.stream()
                .map(OptionalStreamExample::topScorerOfTeam)
                .flatMap(Optional::stream)
                .toList();
    }

    private static Optional<Player> topScorerOfTeam(String teamName) {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .filter(player -> player.team().equals(teamName))
                .max(Comparator.comparingInt(Player::goals));
    }

    public static void main(String[] args) {
        System.out.println(topScorersOfTeams(List.of("Arsenal", "UnknownFC", "Liverpool")));
    }
}
