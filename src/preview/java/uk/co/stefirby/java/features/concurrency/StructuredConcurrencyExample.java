package uk.co.stefirby.java.features.concurrency;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 21 — PREVIEW API (JEP 453): structured concurrency treats a group of
 * related tasks as one unit of work. {@link StructuredTaskScope.ShutdownOnFailure}
 * forks subtasks inside a scope that joins them together and cancels the
 * rest as soon as one fails, so the two Premier League queries below succeed
 * or fail as a unit. {@code StructuredTaskScope} is a <em>preview</em> API on
 * JDK 21, which is why this class lives in the dedicated {@code preview}
 * source set compiled with {@code --enable-preview}.
 */
public class StructuredConcurrencyExample {

    /**
     * The combined result of the two dataset subtask queries.
     *
     * @param topScorer  the name of the player with the most goals
     * @param totalGoals the sum of every player's goals
     */
    public record MatchdaySummary(String topScorer, int totalGoals) {
    }

    /**
     * Runs the top-scorer and total-goals queries as two subtasks of one
     * scope and combines their answers.
     *
     * @return both queries' answers as one summary
     * @throws InterruptedException if interrupted while joining the scope
     * @throws ExecutionException   if either subtask fails
     */
    public static MatchdaySummary fetchMatchdaySummary() throws InterruptedException, ExecutionException {
        Map.Entry<String, Integer> results = runBoth(
                StructuredConcurrencyExample::topScorerName,
                StructuredConcurrencyExample::totalGoals);
        return new MatchdaySummary(results.getKey(), results.getValue());
    }

    /**
     * Forks both tasks into one {@code ShutdownOnFailure} scope: if either
     * fails, the scope cancels the other and rethrows the failure, so the
     * pair never yields a partial result.
     *
     * @param first  the first subtask
     * @param second the second subtask
     * @param <A>    the first subtask's result type
     * @param <B>    the second subtask's result type
     * @return both subtasks' results, only if both succeeded
     * @throws InterruptedException if interrupted while joining the scope
     * @throws ExecutionException   if either subtask fails
     */
    public static <A, B> Map.Entry<A, B> runBoth(Callable<A> first, Callable<B> second)
            throws InterruptedException, ExecutionException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            StructuredTaskScope.Subtask<A> firstTask = scope.fork(first);
            StructuredTaskScope.Subtask<B> secondTask = scope.fork(second);
            scope.join().throwIfFailed();
            return Map.entry(firstTask.get(), secondTask.get());
        }
    }

    private static String topScorerName() {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .max(Comparator.comparingInt(Player::goals))
                .map(Player::name)
                .orElseThrow();
    }

    private static int totalGoals() {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .mapToInt(Player::goals)
                .sum();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println(fetchMatchdaySummary());
    }
}
