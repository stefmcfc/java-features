package uk.co.stefirby.java.features.streams;

import java.util.List;
import java.util.stream.Stream;

/**
 * Java 9: the {@code Stream.iterate(seed, hasNext, next)} predicate
 * overload — a bounded iterative stream expressed like a classic
 * {@code for} loop, with no explicit {@code limit()} call.
 */
public class StreamIterateExample {

    /**
     * @return the Premier League matchweek numbers 1 through 38, generated
     *         by the predicate overload of {@code iterate}
     */
    public static List<Integer> matchweekNumbers() {
        return Stream.iterate(1, week -> week <= 38, week -> week + 1)
                .toList();
    }

    public static void main(String[] args) {
        System.out.println(matchweekNumbers());
    }
}
