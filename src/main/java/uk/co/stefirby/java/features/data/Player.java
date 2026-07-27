package uk.co.stefirby.java.features.data;

/**
 * A Premier League player with a season's worth of headline stats, modelled
 * as a record (final since JDK 16, previewed in JDK 14/15) — the canonical
 * "data carrier" shape the later feature stages query against.
 *
 * @param name          the player's full name
 * @param team          the name of the player's club; always matches a
 *                      {@link Team#name()} in {@link PremierLeagueDataBase}
 * @param position      the playing position, e.g. {@code "Forward"}
 * @param nationality   the country the player represents
 * @param goals         league goals scored this season
 * @param assists       league assists this season
 * @param appearances   league appearances this season
 * @param minutesPlayed league minutes played this season
 */
public record Player(
        String name,
        String team,
        String position,
        String nationality,
        int goals,
        int assists,
        int appearances,
        int minutesPlayed) {
}
