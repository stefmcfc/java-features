package uk.co.stefirby.java.features.data;

import java.time.LocalDate;

/**
 * A completed Premier League fixture, modelled as a record (final since
 * JDK 16, previewed in JDK 14/15).
 *
 * @param homeTeam  the home club's name; always matches a
 *                  {@link Team#name()} in {@link PremierLeagueDataBase}
 * @param awayTeam  the away club's name; always matches a
 *                  {@link Team#name()} in {@link PremierLeagueDataBase}
 * @param homeGoals goals scored by the home team
 * @param awayGoals goals scored by the away team
 * @param date      the day the match was played
 */
public record Match(
        String homeTeam,
        String awayTeam,
        int homeGoals,
        int awayGoals,
        LocalDate date) {
}
