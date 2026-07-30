package uk.co.stefirby.java.features.optional;

import java.util.Optional;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 9: {@code Optional.ifPresentOrElse()} — runs one of two actions
 * depending on whether the {@code Optional} holds a value, replacing the
 * present-check-then-get()/orElse dance.
 */
public class OptionalIfPresentOrElseExample {

    /**
     * @param playerName the player to look up
     * @return a description of the player and their team, or a
     *         not-found message if no player has that name
     */
    public static String describePlayerLookup(String playerName) {
        Optional<Player> player = PremierLeagueDataBase.getAllPlayers().stream()
                .filter(candidate -> candidate.name().equals(playerName))
                .findFirst();

        StringBuilder description = new StringBuilder();
        player.ifPresentOrElse(
                found -> description.append(found.name()).append(" plays for ").append(found.team()),
                () -> description.append("No player named ").append(playerName));
        return description.toString();
    }

    public static void main(String[] args) {
        System.out.println(describePlayerLookup("Mohamed Salah"));
        System.out.println(describePlayerLookup("Alan Shearer"));
    }
}
