package uk.co.stefirby.java.features.concurrency

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * STAGE-9-AC-01: when the virtual-threads (21) example runs its concurrent
 * PL queries, every task executes on a virtual thread, asserted via
 * Thread::isVirtual captured inside each task.
 */
class VirtualThreadsExampleSpec extends Specification {

    def "STAGE-9-AC-01: every concurrent PL query task executes on a virtual thread"() {
        when: "the virtual-threads example runs its concurrent PL queries"
            def results = VirtualThreadsExample.runConcurrentQueries()

        then: "every task reports Thread.currentThread().isVirtual() as true"
            !results.isEmpty()
            results.every { it.ranOnVirtualThread() }
    }

    def "STAGE-9-AC-01: the concurrent queries return the same answers as querying the dataset directly"() {
        given: "the answers computed directly from the dataset"
            def players = PremierLeagueDataBase.getAllPlayers()
            def expectedTopScorer = players.max { it.goals() }.name()
            def expectedTotalGoals = players.sum { it.goals() } as String
            def expectedTeamCount = PremierLeagueDataBase.getAllTeams().size() as String

        when: "the virtual-threads example runs its concurrent PL queries"
            def answersByQuery = VirtualThreadsExample.runConcurrentQueries()
                    .collectEntries { [it.query(), it.answer()] }

        then: "each named query carries the directly-computed answer"
            answersByQuery["top scorer"] == expectedTopScorer
            answersByQuery["total goals"] == expectedTotalGoals
            answersByQuery["team count"] == expectedTeamCount
    }
}
