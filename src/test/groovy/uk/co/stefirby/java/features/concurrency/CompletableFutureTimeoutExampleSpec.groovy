package uk.co.stefirby.java.features.concurrency

import spock.lang.Specification
import uk.co.stefirby.java.features.streams.StreamToListExample

import java.time.Duration
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeoutException

/**
 * STAGE-12-AC-02: when the CompletableFuture timeout additions (9) example
 * runs a dataset query that outlives its deadline, orTimeout() completes
 * the future exceptionally with a TimeoutException and completeOnTimeout()
 * completes it with the given fallback value; a query that finishes in
 * time completes normally with its result.
 */
class CompletableFutureTimeoutExampleSpec extends Specification {

    def "STAGE-12-AC-02: orTimeout() completes exceptionally with a TimeoutException when the query outlives its deadline"() {
        when: "a query that outlives its deadline runs under orTimeout()"
            def future = CompletableFutureTimeoutExample.queryTopScorersOrTimeout(
                    Duration.ofMillis(500), Duration.ofMillis(50))
            future.get()

        then: "the future completes exceptionally with a TimeoutException"
            def e = thrown(ExecutionException)
            e.cause instanceof TimeoutException
    }

    def "STAGE-12-AC-02: completeOnTimeout() completes with the fallback value when the query outlives its deadline"() {
        given: "a fallback result"
            def fallback = ["fallback"]

        when: "a query that outlives its deadline runs under completeOnTimeout()"
            def result = CompletableFutureTimeoutExample.queryTopScorersWithFallback(
                    Duration.ofMillis(500), Duration.ofMillis(50), fallback).get()

        then: "the future completes with the fallback value"
            result == fallback
    }

    def "STAGE-12-AC-02: a query that finishes in time completes normally with its result"() {
        when: "a query that finishes well inside its deadline runs under orTimeout()"
            def result = CompletableFutureTimeoutExample.queryTopScorersOrTimeout(
                    Duration.ofMillis(0), Duration.ofSeconds(5)).get()

        then: "the future completes normally with the query's real result"
            result == StreamToListExample.topScorerNames()
    }
}
