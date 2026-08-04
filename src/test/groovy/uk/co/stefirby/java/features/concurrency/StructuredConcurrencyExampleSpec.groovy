package uk.co.stefirby.java.features.concurrency

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

import java.util.concurrent.ExecutionException

/**
 * STAGE-9-AC-02: where the build enables the preview flag JDK 21 requires
 * (the dedicated preview source set), the structured-concurrency example
 * runs two dataset queries as subtasks of one StructuredTaskScope that
 * succeeds or fails as a unit, and its Javadoc header explicitly flags the
 * API as preview.
 */
class StructuredConcurrencyExampleSpec extends Specification {

    def "STAGE-9-AC-02: two dataset queries run as subtasks of one scope and succeed as a unit"() {
        given: "the two queries' answers computed directly from the dataset"
            def players = PremierLeagueDataBase.getAllPlayers()
            def expectedTopScorer = players.max { it.goals() }.name()
            def expectedTotalGoals = players.sum { it.goals() }

        when: "the structured-concurrency example runs both queries in one scope"
            def summary = StructuredConcurrencyExample.fetchMatchdaySummary()

        then: "the scope's combined result carries both subtasks' answers"
            summary.topScorer() == expectedTopScorer
            summary.totalGoals() == expectedTotalGoals
    }

    def "STAGE-9-AC-02: if one subtask fails, the whole scope fails as a unit"() {
        when: "one of the two subtasks forked into the scope throws"
            StructuredConcurrencyExample.runBoth(
                    { "the healthy subtask's answer" },
                    { throw new IllegalStateException("dataset query failed") })

        then: "the scope surfaces the failure instead of a partial result"
            def e = thrown(ExecutionException)
            e.cause instanceof IllegalStateException
            e.cause.message == "dataset query failed"
    }

    def "STAGE-9-AC-02: the example's Javadoc header explicitly flags the API as preview"() {
        given: "the structured-concurrency example's source in the preview source set"
            def source = new File(
                    "src/preview/java/uk/co/stefirby/java/features/concurrency/StructuredConcurrencyExample.java")

        expect: "the class Javadoc header flags the API as preview"
            source.exists()
            source.text.substring(0, source.text.indexOf("public class"))
                    .toLowerCase().contains("preview")
    }
}
