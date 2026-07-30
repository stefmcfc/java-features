package uk.co.stefirby.java.features.streams;

import java.util.List;
import java.util.Objects;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;
import uk.co.stefirby.java.features.data.Team;

/**
 * Java 11/16/21: the classic longest-common-prefix interview exercise,
 * tailored to Premier League team names and solved two ways with modern
 * Stream idioms — {@code String.isBlank()} (11) to discard unusable
 * entries, {@code Stream.toList()} (16) for the filtered snapshots, and
 * the SequencedCollection accessors {@code getFirst()}/{@code getLast()}
 * (21) instead of index arithmetic.
 *
 * <p>Both strategies ignore null and blank entries and return {@code ""}
 * when no usable names remain. Matching is case-sensitive.
 */
public class LongestCommonPrefixExample {

    /**
     * Reduction strategy: fold the names pairwise, shrinking the candidate
     * prefix as each name is folded in. A single-element list reduces to
     * itself; an empty one falls back to {@code ""} via the
     * {@code Optional} that {@code reduce} returns.
     *
     * @param teamNames the names to scan; may be null or hold null/blank entries
     * @return the longest prefix shared by every usable name
     */
    public static String commonPrefixByReduction(List<String> teamNames) {
        return usableNames(teamNames).stream()
                .reduce(LongestCommonPrefixExample::commonPrefixOf)
                .orElse("");
    }

    /**
     * Sorting strategy: after a lexicographic sort, the first and last
     * names are the most different pair, so their shared prefix is shared
     * by everything between them — one comparison instead of a fold.
     *
     * @param teamNames the names to scan; may be null or hold null/blank entries
     * @return the longest prefix shared by every usable name
     */
    public static String commonPrefixBySorting(List<String> teamNames) {
        var sorted = usableNames(teamNames).stream()
                .sorted()
                .toList();

        if (sorted.isEmpty()) {
            return "";
        }
        return commonPrefixOf(sorted.getFirst(), sorted.getLast());
    }

    private static List<String> usableNames(List<String> names) {
        if (names == null) {
            return List.of();
        }
        return names.stream()
                .filter(Objects::nonNull)
                .filter(name -> !name.isBlank())
                .toList();
    }

    private static String commonPrefixOf(String a, String b) {
        int i = 0;
        while (i < a.length() && i < b.length() && a.charAt(i) == b.charAt(i)) {
            i++;
        }
        return a.substring(0, i);
    }

    public static void main(String[] args) {
        List<String> allTeams = PremierLeagueDataBase.getAllTeams().stream()
                .map(Team::name)
                .toList();
        List<String> manchesterClubs = allTeams.stream()
                .filter(name -> name.startsWith("Man"))
                .toList();

        System.out.println("All teams:        '" + commonPrefixByReduction(allTeams) + "'");
        System.out.println("Manchester clubs: '" + commonPrefixBySorting(manchesterClubs) + "'");
    }
}
