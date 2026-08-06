package uk.co.stefirby.java.features.streams

import spock.lang.Specification
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * Covers STAGE-11-AC-06 — LocalDate.datesUntil() (9) streams every
 * Saturday of the season week by week from the first match date to just
 * past the last, so every match date in the dataset appears in the result.
 */
class LocalDateDatesUntilExampleSpec extends Specification {

    def "STAGE-11-AC-06: seasonSaturdays includes every match date in the dataset"() {
        given: "every match date in the dataset"
            def matchDates = PremierLeagueDataBase.getAllMatches().collect { it.date() }

        when: "seasonSaturdays is called"
            def saturdays = LocalDateDatesUntilExample.seasonSaturdays()

        then: "every match date appears among the resulting dates"
            matchDates.every { saturdays.contains(it) }
    }
}
