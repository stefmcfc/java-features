package uk.co.stefirby.java.features.concurrency;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 21: virtual threads (JEP 444) are cheap, JVM-scheduled threads —
 * {@link Executors#newVirtualThreadPerTaskExecutor()} gives every submitted
 * task its own virtual thread, so blocking per-task code scales without
 * pooling. Each Premier League query below records
 * {@link Thread#isVirtual()} from inside its task to prove where it ran.
 */
public class VirtualThreadsExample {

    /**
     * One concurrent dataset query's outcome.
     *
     * @param query              the query's name, e.g. {@code "top scorer"}
     * @param answer             the query's result, rendered as text
     * @param ranOnVirtualThread whether the task observed
     *                           {@link Thread#isVirtual()} while computing
     */
    public record QueryResult(String query, String answer, boolean ranOnVirtualThread) {
    }

    /**
     * Runs three independent Premier League queries concurrently, one
     * virtual thread per task.
     *
     * @return each query's answer plus whether it ran on a virtual thread
     * @throws InterruptedException if interrupted while awaiting a task
     * @throws ExecutionException   if a query task fails
     */
    public static List<QueryResult> runConcurrentQueries() throws InterruptedException, ExecutionException {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<QueryResult>> futures = List.of(
                    executor.submit(() -> answer("top scorer", VirtualThreadsExample::topScorerName)),
                    executor.submit(() -> answer("total goals", VirtualThreadsExample::totalGoals)),
                    executor.submit(() -> answer("team count", VirtualThreadsExample::teamCount)));
            List<QueryResult> results = new ArrayList<>();
            for (Future<QueryResult> future : futures) {
                results.add(future.get());
            }
            return List.copyOf(results);
        }
    }

    private static QueryResult answer(String query, Supplier<String> queryFn) {
        return new QueryResult(query, queryFn.get(), Thread.currentThread().isVirtual());
    }

    private static String topScorerName() {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .max(Comparator.comparingInt(Player::goals))
                .map(Player::name)
                .orElseThrow();
    }

    private static String totalGoals() {
        return String.valueOf(PremierLeagueDataBase.getAllPlayers().stream()
                .mapToInt(Player::goals)
                .sum());
    }

    private static String teamCount() {
        return String.valueOf(PremierLeagueDataBase.getAllTeams().size());
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        runConcurrentQueries().forEach(System.out::println);
    }
}
