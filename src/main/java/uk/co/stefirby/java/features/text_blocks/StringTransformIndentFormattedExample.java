package uk.co.stefirby.java.features.text_blocks;

import uk.co.stefirby.java.features.data.PremierLeagueDataBase;
import uk.co.stefirby.java.features.data.Team;

/**
 * Java 12: {@code String.transform()} pipes the string through an
 * arbitrary {@code Function}, letting a fluent chain apply a
 * transformation without breaking out into a local variable. Java 12:
 * {@code String.indent()} normalizes line-leading whitespace, adding
 * {@code n} spaces per line. Java 15: {@code String.formatted()} is an
 * instance-method shorthand for {@code String.format(this, args)}.
 * Combined here into one small club report over PL text data.
 */
public class StringTransformIndentFormattedExample {

    /**
     * @param teamName a club name
     * @return the name upper-cased, via {@code transform()}
     */
    public static String shoutedName(String teamName) {
        return teamName.transform(String::toUpperCase);
    }

    /**
     * @param stadium a club's home ground
     * @return a {@code "Stadium: ..."} line indented 4 spaces, via {@code indent()}
     */
    public static String indentedStadiumLine(String stadium) {
        return ("Stadium: " + stadium).indent(4);
    }

    /**
     * @param team a club
     * @return a {@code "name, founded YYYY"} summary, via {@code formatted()}
     */
    public static String foundedSummary(Team team) {
        return "%s, founded %d".formatted(team.name(), team.founded());
    }

    /**
     * @param team a club
     * @return a short club report combining {@link #shoutedName},
     *         {@link #indentedStadiumLine}, and {@link #foundedSummary}
     */
    public static String clubReport(Team team) {
        return shoutedName(team.name()) + "\n" + indentedStadiumLine(team.stadium()) + "\n" + foundedSummary(team);
    }

    public static void main(String[] args) {
        System.out.println(clubReport(PremierLeagueDataBase.getAllTeams().getFirst()));
    }
}
