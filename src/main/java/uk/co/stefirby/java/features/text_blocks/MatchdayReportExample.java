package uk.co.stefirby.java.features.text_blocks;

import uk.co.stefirby.java.features.data.Match;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 15: text blocks ({@code """}) build a multi-line matchday report
 * without escape-heavy concatenation — the literal reads as the printed
 * layout, and {@link String#formatted} slots the match details straight in.
 */
public class MatchdayReportExample {

    /**
     * @param match the fixture to report on
     * @return a multi-line matchday report naming both teams, the score,
     *         and the date, terminated by a trailing newline
     */
    public static String matchdayReport(Match match) {
        return """
                Matchday Report
                ===============
                %s vs %s
                Score: %d - %d
                Date: %s
                """.formatted(match.homeTeam(), match.awayTeam(), match.homeGoals(), match.awayGoals(), match.date());
    }

    public static void main(String[] args) {
        System.out.println(matchdayReport(PremierLeagueDataBase.getAllMatches().getFirst()));
    }
}
