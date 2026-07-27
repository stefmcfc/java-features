package uk.co.stefirby.java.features.data;

/**
 * A Premier League club, modelled as a record (final since JDK 16,
 * previewed in JDK 14/15).
 *
 * @param name    the club's name; referenced by {@link Player#team()} and
 *                {@link Match#homeTeam()}/{@link Match#awayTeam()}
 * @param stadium the club's home ground
 * @param manager the current first-team manager
 * @param founded the year the club was founded
 */
public record Team(
        String name,
        String stadium,
        String manager,
        int founded) {
}
