package uk.co.stefirby.java.features.streams;

import java.util.List;
import uk.co.stefirby.java.features.data.Team;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 21: Sequenced Collections — {@code List.reversed()} gives a
 * reverse-ordered view of the list, and streaming that view walks the
 * elements last-to-first without copying or sorting.
 */
public class SequencedCollectionStreamExample {

    /**
     * @return the PL teams in reverse dataset order, streamed from the
     *         {@code reversed()} view
     */
    public static List<Team> teamsLastToFirst() {
        return PremierLeagueDataBase.getAllTeams().reversed().stream()
                .toList();
    }

    public static void main(String[] args) {
        teamsLastToFirst().forEach(System.out::println);
    }
}
