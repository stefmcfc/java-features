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
        when:
        def leaders = TakeWhileDropWhileExample.leadingScorersAbove(THRESHOLD)

        then:
        !leaders.isEmpty()
        leaders.every { it.goals() > THRESHOLD }
    }

    def "STAGE-3-AC-04: remainingScorers returns exactly the remainder"() {
        given:
        def sortedDescending = PremierLeagueDataBase.getAllPlayers()
                .toSorted { a, b -> b.goals() <=> a.goals() }

        when:
        def leaders = TakeWhileDropWhileExample.leadingScorersAbove(THRESHOLD)
        def remainder = TakeWhileDropWhileExample.remainingScorers(THRESHOLD)

        then: "the first of the remainder is at or below the threshold"
        remainder.first().goals() <= THRESHOLD

        and: "take + drop reassemble the full sorted list"
        leaders + remainder == sortedDescending
    }
}
