package uk.co.stefirby.java.features.streams

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * Covers STAGE-3-AC-04 — takeWhile()/dropWhile() (9) applied to players
 * sorted by goals descending: takeWhile returns only the leading players
 * above a goals threshold, dropWhile returns exactly the remainder.
 */
class TakeWhileDropWhileExampleSpec extends Specification {

    static final int THRESHOLD = 10

    def "STAGE-3-AC-04: leadingScorersAbove returns only the leading players above the threshold"() {
        when: "leadingScorersAbove is called with the threshold"
            def leaders = TakeWhileDropWhileExample.leadingScorersAbove(THRESHOLD)

        then: "every returned player is above the threshold"
            !leaders.isEmpty()
            leaders.every { it.goals() > THRESHOLD }
    }

    def "STAGE-3-AC-04: remainingScorers returns exactly the remainder"() {
        given: "the full player list sorted by goals descending"
            def sortedDescending = PremierLeagueDataBase.getAllPlayers()
                    .toSorted { a, b -> b.goals() <=> a.goals() }

        when: "leadingScorersAbove and remainingScorers are called with the same threshold"
            def leaders = TakeWhileDropWhileExample.leadingScorersAbove(THRESHOLD)
            def remainder = TakeWhileDropWhileExample.remainingScorers(THRESHOLD)

        then: "the first of the remainder is at or below the threshold"
            remainder.first().goals() <= THRESHOLD

        and: "take + drop reassemble the full sorted list"
            leaders + remainder == sortedDescending
    }
}
