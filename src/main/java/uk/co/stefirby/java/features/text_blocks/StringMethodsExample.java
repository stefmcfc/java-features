package uk.co.stefirby.java.features.text_blocks;

import java.util.List;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 11: the small-but-handy {@code String} additions — {@code isBlank()},
 * {@code strip()}, {@code repeat()}, and {@code lines()} — applied to
 * Premier League text data. {@code strip()} removes all Unicode whitespace
 * (unlike {@code trim()}, which stops at U+0020), and {@code lines()} splits
 * on any line terminator without a regex.
 */
public class StringMethodsExample {

    /**
     * @param line one line of a team sheet
     * @return whether the line is empty or all whitespace, per {@code isBlank()}
     */
    public static boolean isBlankLine(String line) {
        return line.isBlank();
    }

    /**
     * @param paddedName a player name padded with (possibly Unicode) whitespace
     * @return the name with leading and trailing whitespace removed, per {@code strip()}
     */
    public static String strippedName(String paddedName) {
        return paddedName.strip();
    }

    /**
     * @param heading a report heading
     * @return an {@code "="} underline exactly as wide as the heading, per {@code repeat()}
     */
    public static String headingUnderline(String heading) {
        return "=".repeat(heading.length());
    }

    /**
     * @param report a multi-line report
     * @return the report's lines in order, terminators removed, per {@code lines()}
     */
    public static List<String> reportLines(String report) {
        return report.lines().toList();
    }

    public static void main(String[] args) {
        String heading = "Top Scorers";
        System.out.println(heading);
        System.out.println(headingUnderline(heading));
        String teamSheet = "  " + PremierLeagueDataBase.getAllPlayers().getFirst().name() + "  \n   \n";
        for (String line : reportLines(teamSheet)) {
            System.out.println(isBlankLine(line) ? "(blank line)" : strippedName(line));
        }
    }
}
