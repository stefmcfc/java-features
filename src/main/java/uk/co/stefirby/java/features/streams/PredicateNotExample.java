package uk.co.stefirby.java.features.streams;

import java.util.List;
import java.util.function.Predicate;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 11: {@code Predicate.not()} — negates an existing predicate without
 * wrapping it in a negated lambda, here filtering out goalkeepers to leave
 * the outfield players.
 */
public class PredicateNotExample {

    private static boolean isGoalkeeper(Player player) {
        return player.position().equals("Goalkeeper");
    }

    /**
     * @return every player whose position is not {@code Goalkeeper}
     */
    public static List<Player> outfieldPlayers() {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .filter(Predicate.not(PredicateNotExample::isGoalkeeper))
                .toList();
    }

    public static void main(String[] args) {
        System.out.println(outfieldPlayers().stream().map(Player::name).toList());
    }
}
