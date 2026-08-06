package uk.co.stefirby.java.features.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 19: {@link Future#state()} plus the paired
 * {@link Future#resultNow()}/{@link Future#exceptionNow()} accessors —
 * inspecting a completed {@link Future} no longer requires catching
 * {@link ExecutionException} just to unwrap its cause, or guessing whether
 * {@code get()} will return or throw.
 */
public class FutureStateExample {

    /**
     * One inspected {@link Future}'s outcome.
     *
     * @param state       the future's terminal {@link Future.State}
     * @param description what {@code resultNow()}/{@code exceptionNow()} reported
     */
    public record QueryOutcome(Future.State state, String description) {
    }

    /**
     * @return the state and result of a dataset query that succeeds,
     *         obtained via {@code resultNow()}
     */
    public static QueryOutcome inspectSuccessful() throws InterruptedException {
        Future<Integer> future = submit(() -> PremierLeagueDataBase.getAllTeams().size());
        return new QueryOutcome(future.state(), "team count: " + future.resultNow());
    }

    /**
     * @return the state and cause of a dataset query that fails, obtained
     *         via {@code exceptionNow()}
     */
    public static QueryOutcome inspectFailed() throws InterruptedException {
        Future<Integer> future = submit(() -> {
            throw new IllegalStateException("dataset query failed");
        });
        return new QueryOutcome(future.state(), "failed: " + future.exceptionNow().getMessage());
    }

    private static <T> Future<T> submit(java.util.concurrent.Callable<T> task) throws InterruptedException {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<T> future = executor.submit(task);
            awaitCompletion(future);
            return future;
        }
    }

    private static void awaitCompletion(Future<?> future) throws InterruptedException {
        try {
            future.get();
        } catch (ExecutionException expectedForFailingQueries) {
            // Draining the exception here lets state()/resultNow()/exceptionNow()
            // be called afterwards without re-throwing.
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(inspectSuccessful());
        System.out.println(inspectFailed());
    }
}
