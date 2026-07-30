package uk.co.stefirby.java.features.streams;

import java.util.stream.Stream;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 9: {@code Stream.ofNullable()} — lifts a possibly-null value into a
 * stream of zero or one elements, so nullable lookup results compose into
 * pipelines without an explicit null check.
 */
public class StreamOfNullableExample {

    private static Player findByNameOrNull(String name) {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .filter(player -> player.name().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * @param name the player name to look up
     * @return an empty stream if the lookup finds nothing, otherwise a
     *         stream of exactly that one player
     */
    public static Stream<Player> lookupAsStream(String name) {
        return Stream.ofNullable(findByNameOrNull(name));
    }

    public static void main(String[] args) {
        System.out.println("Known player:   " + lookupAsStream("Mohamed Salah").toList());
        System.out.println("Unknown player: " + lookupAsStream("Alan Shearer").toList());
    }
}
