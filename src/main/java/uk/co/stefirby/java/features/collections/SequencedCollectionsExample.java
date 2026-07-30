package uk.co.stefirby.java.features.collections;

import java.util.Comparator;
import java.util.List;
import uk.co.stefirby.java.features.data.Match;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 21: Sequenced Collections — {@code List} now implements
 * {@code SequencedCollection}, giving direct {@code getFirst()} /
 * {@code getLast()} access to the ends of an ordered collection and a
 * {@code reversed()} view in opposite order, with no index arithmetic.
 */
public class SequencedCollectionsExample {

    /**
     * @return the season's matches ordered by date
     */
    public static List<Match> matchesByDate() {
        return PremierLeagueDataBase.getAllMatches().stream()
                .sorted(Comparator.comparing(Match::date))
                .toList();
    }

    /**
     * @return the season's opening match, via {@code getFirst()}
     */
    public static Match openingMatch() {
        return matchesByDate().getFirst();
    }

    /**
     * @return the season's most recent match, via {@code getLast()}
     */
    public static Match latestMatch() {
        return matchesByDate().getLast();
    }

    /**
     * @return the season's matches latest-first, via {@code reversed()}
     */
    public static List<Match> matchesLatestFirst() {
        return matchesByDate().reversed();
    }

    public static void main(String[] args) {
        System.out.println("Opening match: " + openingMatch());
        System.out.println("Latest match:  " + latestMatch());
        System.out.println("Latest first:  " + matchesLatestFirst());
    }
}
