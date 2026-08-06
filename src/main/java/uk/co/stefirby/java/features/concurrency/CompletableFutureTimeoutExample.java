package uk.co.stefirby.java.features.concurrency;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import uk.co.stefirby.java.features.streams.StreamToListExample;

/**
 * Java 9: {@link CompletableFuture#orTimeout} and
 * {@link CompletableFuture#completeOnTimeout} attach a deadline to an
 * async computation without hand-rolled {@code ScheduledExecutorService}
 * bookkeeping. Both are exercised here against a deliberately slow
 * top-scorers query: {@code orTimeout()} lets a blown deadline fail the
 * future, {@code completeOnTimeout()} lets it fall back to a default
 * instead.
 */
public class CompletableFutureTimeoutExample {

    /**
     * @param simulatedDelay how long the query artificially sleeps before
     *                       returning, to make deadlines easy to blow past
     *                       or comfortably beat
     * @return a future that resolves to the top-scorer names after the
     *         simulated delay
     */
    public static CompletableFuture<List<String>> queryTopScorers(Duration simulatedDelay) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(simulatedDelay);
            return StreamToListExample.topScorerNames();
        });
    }

    /**
     * @param simulatedDelay how long the underlying query sleeps
     * @param deadline       the deadline the query must finish within
     * @return a future that completes normally if the query finishes
     *         within {@code deadline}, or exceptionally with a
     *         {@link java.util.concurrent.TimeoutException} otherwise
     */
    public static CompletableFuture<List<String>> queryTopScorersOrTimeout(Duration simulatedDelay, Duration deadline) {
        return queryTopScorers(simulatedDelay).orTimeout(deadline.toMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * @param simulatedDelay how long the underlying query sleeps
     * @param deadline       the deadline the query must finish within
     * @param fallback       the value to complete with if the deadline is missed
     * @return a future that completes with the query's result if it
     *         finishes within {@code deadline}, or with {@code fallback} otherwise
     */
    public static CompletableFuture<List<String>> queryTopScorersWithFallback(
            Duration simulatedDelay, Duration deadline, List<String> fallback) {
        return queryTopScorers(simulatedDelay).completeOnTimeout(fallback, deadline.toMillis(), TimeUnit.MILLISECONDS);
    }

    private static void sleep(Duration duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> onTime = queryTopScorersOrTimeout(Duration.ofMillis(0), Duration.ofSeconds(5)).get();
        System.out.println("Finished in time: " + onTime);

        try {
            queryTopScorersOrTimeout(Duration.ofMillis(500), Duration.ofMillis(50)).get();
        } catch (Exception e) {
            System.out.println("orTimeout() failed as expected: " + e.getCause());
        }

        List<String> fallback = queryTopScorersWithFallback(
                Duration.ofMillis(500), Duration.ofMillis(50), List.of("(timed out)")).get();
        System.out.println("completeOnTimeout() fell back to: " + fallback);
    }
}
