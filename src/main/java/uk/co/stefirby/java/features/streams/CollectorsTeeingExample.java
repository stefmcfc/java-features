package uk.co.stefirby.java.features.streams;

import java.util.stream.Collectors;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 12: {@code Collectors.teeing()} — feeds every stream element to two
 * downstream collectors and merges their results, so two aggregates come
 * out of a single pass over the data.
 */
public class CollectorsTeeingExample {

    /**
     * The two aggregates {@link #leagueGoalStats()} computes together.
     *
     * @param totalGoals            the league's total goals
     * @param averageGoalsPerPlayer the mean goals per player
     */
    public record GoalStats(int totalGoals, double averageGoalsPerPlayer) {
    }

    /**
     * @return the league's total goals and average goals per player,
     *         computed in a single pass via {@code teeing}
     */
    public static GoalStats leagueGoalStats() {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .collect(Collectors.teeing(
                        Collectors.summingInt(Player::goals),
                        Collectors.averagingInt(Player::goals),
                        GoalStats::new));
    }

    public static void main(String[] args) {
        System.out.println(leagueGoalStats());
    }
}
