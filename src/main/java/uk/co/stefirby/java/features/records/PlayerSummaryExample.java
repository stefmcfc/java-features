package uk.co.stefirby.java.features.records;

import java.util.List;
import java.util.Optional;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 16: records with a compact constructor that validates its
 * components at construction time — {@code PlayerSummary} rejects an
 * impossible stat line (e.g. negative goals) before it can be stored or
 * serialised, rather than deferring the check to whoever reads the fields.
 */
public class PlayerSummaryExample {

    /**
     * A player stats DTO, the shape the stage-7 REST endpoint serialises.
     *
     * @param name    the player's full name
     * @param team    the name of the player's club
     * @param goals   league goals scored this season; must not be negative
     * @param assists league assists this season; must not be negative
     */
    public record PlayerSummary(String name, String team, int goals, int assists) {

        public PlayerSummary {
            if (goals < 0) {
                throw new IllegalArgumentException("goals must not be negative: " + goals);
            }
            if (assists < 0) {
                throw new IllegalArgumentException("assists must not be negative: " + assists);
            }
        }
    }

    /**
     * @param id the player's position in {@link PremierLeagueDataBase#getAllPlayers()}
     * @return the matching player as a {@link PlayerSummary}, or empty if no
     *         player exists at that position
     */
    public static Optional<PlayerSummary> findById(int id) {
        List<Player> players = PremierLeagueDataBase.getAllPlayers();
        if (id < 0 || id >= players.size()) {
            return Optional.empty();
        }
        Player player = players.get(id);
        return Optional.of(new PlayerSummary(player.name(), player.team(), player.goals(), player.assists()));
    }

    public static void main(String[] args) {
        System.out.println(findById(0));
        System.out.println(findById(-1));
    }
}
