package uk.co.stefirby.java.features.streams;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import uk.co.stefirby.java.features.data.Match;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 9: {@code LocalDate.datesUntil()} — streams a date range without
 * building an intermediate list, here stepping week by week from the
 * season's first match date to just past its last, landing on every
 * matchweek's Saturday.
 */
public class LocalDateDatesUntilExample {

    /**
     * @return every Saturday of the season, week by week from the first
     *         match date up to and including the last match date
     */
    public static List<LocalDate> seasonSaturdays() {
        List<Match> matches = PremierLeagueDataBase.getAllMatches();
        LocalDate first = matches.stream().map(Match::date).min(Comparator.naturalOrder()).orElseThrow();
        LocalDate last = matches.stream().map(Match::date).max(Comparator.naturalOrder()).orElseThrow();
        return first.datesUntil(last.plusWeeks(1), Period.ofWeeks(1)).toList();
    }

    public static void main(String[] args) {
        System.out.println(seasonSaturdays());
    }
}
