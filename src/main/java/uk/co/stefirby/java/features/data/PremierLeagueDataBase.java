package uk.co.stefirby.java.features.data;

import java.time.LocalDate;
import java.util.List;

/**
 * The shared in-memory Premier League dataset every feature stage queries.
 * The lists are built with {@link List#of} (JDK 9), so they are structurally
 * immutable — any attempt to mutate them throws
 * {@link UnsupportedOperationException}. There is no I/O and no database
 * behind this class; the data is hand-picked, plausible sample data.
 */
public final class PremierLeagueDataBase {

    private static final List<Team> TEAMS = List.of(
            new Team("Arsenal", "Emirates Stadium", "Mikel Arteta", 1886),
            new Team("Chelsea", "Stamford Bridge", "Enzo Maresca", 1905),
            new Team("Liverpool", "Anfield", "Arne Slot", 1892),
            new Team("Manchester City", "Etihad Stadium", "Pep Guardiola", 1880),
            new Team("Manchester United", "Old Trafford", "Ruben Amorim", 1878),
            new Team("Tottenham Hotspur", "Tottenham Hotspur Stadium", "Thomas Frank", 1882));

    private static final List<Player> PLAYERS = List.of(
            new Player("Bukayo Saka", "Arsenal", "Forward", "England", 12, 13, 35, 2940),
            new Player("Martin Odegaard", "Arsenal", "Midfielder", "Norway", 8, 10, 34, 2812),
            new Player("Declan Rice", "Arsenal", "Midfielder", "England", 4, 8, 36, 3105),
            new Player("William Saliba", "Arsenal", "Defender", "France", 2, 0, 37, 3320),
            new Player("Cole Palmer", "Chelsea", "Midfielder", "England", 15, 8, 37, 3122),
            new Player("Nicolas Jackson", "Chelsea", "Forward", "Senegal", 10, 5, 30, 2287),
            new Player("Moises Caicedo", "Chelsea", "Midfielder", "Ecuador", 1, 3, 38, 3390),
            new Player("Enzo Fernandez", "Chelsea", "Midfielder", "Argentina", 7, 9, 35, 2904),
            new Player("Mohamed Salah", "Liverpool", "Forward", "Egypt", 29, 18, 38, 3315),
            new Player("Luis Diaz", "Liverpool", "Forward", "Colombia", 13, 5, 36, 2778),
            new Player("Alexis Mac Allister", "Liverpool", "Midfielder", "Argentina", 5, 5, 33, 2610),
            new Player("Virgil van Dijk", "Liverpool", "Defender", "Netherlands", 3, 1, 37, 3330),
            new Player("Erling Haaland", "Manchester City", "Forward", "Norway", 27, 5, 31, 2552),
            new Player("Phil Foden", "Manchester City", "Midfielder", "England", 9, 6, 28, 2013),
            new Player("Bernardo Silva", "Manchester City", "Midfielder", "Portugal", 4, 7, 32, 2705),
            new Player("Ederson", "Manchester City", "Goalkeeper", "Brazil", 0, 1, 30, 2700),
            new Player("Bruno Fernandes", "Manchester United", "Midfielder", "Portugal", 8, 10, 36, 3140),
            new Player("Amad Diallo", "Manchester United", "Forward", "Ivory Coast", 8, 6, 32, 2233),
            new Player("Kobbie Mainoo", "Manchester United", "Midfielder", "England", 2, 1, 26, 1708),
            new Player("Son Heung-min", "Tottenham Hotspur", "Forward", "South Korea", 10, 9, 33, 2645),
            new Player("James Maddison", "Tottenham Hotspur", "Midfielder", "England", 9, 7, 32, 2512),
            new Player("Cristian Romero", "Tottenham Hotspur", "Defender", "Argentina", 2, 0, 28, 2430));

    private static final List<Match> MATCHES = List.of(
            new Match("Manchester United", "Arsenal", 0, 2, LocalDate.of(2025, 8, 16)),
            new Match("Liverpool", "Tottenham Hotspur", 3, 1, LocalDate.of(2025, 8, 23)),
            new Match("Chelsea", "Manchester City", 1, 1, LocalDate.of(2025, 9, 13)),
            new Match("Arsenal", "Liverpool", 2, 2, LocalDate.of(2025, 10, 4)),
            new Match("Tottenham Hotspur", "Chelsea", 0, 3, LocalDate.of(2025, 11, 1)),
            new Match("Manchester City", "Manchester United", 4, 1, LocalDate.of(2025, 12, 6)),
            new Match("Liverpool", "Chelsea", 2, 0, LocalDate.of(2026, 1, 17)),
            new Match("Arsenal", "Tottenham Hotspur", 3, 0, LocalDate.of(2026, 2, 7)),
            new Match("Manchester City", "Liverpool", 1, 2, LocalDate.of(2026, 3, 14)),
            new Match("Manchester United", "Chelsea", 2, 2, LocalDate.of(2026, 4, 11)));

    private PremierLeagueDataBase() {
    }

    /**
     * @return every player in the dataset, as an immutable list
     */
    public static List<Player> getAllPlayers() {
        return PLAYERS;
    }

    /**
     * @return every team in the dataset, as an immutable list
     */
    public static List<Team> getAllTeams() {
        return TEAMS;
    }

    /**
     * @return every completed match in the dataset, as an immutable list
     */
    public static List<Match> getAllMatches() {
        return MATCHES;
    }

    /**
     * Prints a quick summary of the dataset so it can be eyeballed without
     * running the test suite.
     */
    public static void main(String[] args) {
        System.out.println("Teams:   " + getAllTeams().size());
        System.out.println("Players: " + getAllPlayers().size());
        System.out.println("Matches: " + getAllMatches().size());
        getAllTeams().forEach(team -> System.out.println("  " + team));
    }
}
