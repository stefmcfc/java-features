package uk.co.stefirby.java.features.var;

import java.util.List;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 10: local variable type inference with {@code var} — the declared
 * type is inferred from the initializer, trimming boilerplate on locals
 * without changing what code executes at runtime. The same query is run
 * twice below, once with {@code var} and once with explicit types, to show
 * they return identical results.
 */
public class VarLocalInferenceExample {

    /**
     * @return the names of every player with more assists than goals this
     *         season, sorted alphabetically, using {@code var} for every
     *         local declaration
     */
    public static List<String> playersWithMoreAssistsThanGoalsUsingVar() {
        var players = PremierLeagueDataBase.getAllPlayers();
        var names = players.stream()
                .filter(player -> player.assists() > player.goals())
                .map(Player::name)
                .sorted()
                .toList();
        return names;
    }

    /**
     * @return the same query as {@link #playersWithMoreAssistsThanGoalsUsingVar()},
     *         with every local declared using its explicit type
     */
    public static List<String> playersWithMoreAssistsThanGoalsExplicit() {
        List<Player> players = PremierLeagueDataBase.getAllPlayers();
        List<String> names = players.stream()
                .filter(player -> player.assists() > player.goals())
                .map(Player::name)
                .sorted()
                .toList();
        return names;
    }

    public static void main(String[] args) {
        System.out.println(playersWithMoreAssistsThanGoalsUsingVar());
    }
}
