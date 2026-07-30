package uk.co.stefirby.java.features.streams;

import java.util.*;

public class InterviewStreamExample {

    private static final List<String> NULL = null;
    private static final List<String> EMPTY = Collections.emptyList();
    private static final List<String> ONE_ENTRY = List.of("one");
    private static final List<String> LIST_CONTAINS_NULL = new ArrayList<>();
    private static final List<String> MULTIPLE_ENTRIES = List.of("star", "starch", "stardust", "stare", "stark");


    public String commonPrefix(List<String> words) {
        if (words == null || words.isEmpty()) {
            return "";
        }

        List<String> nonNullWords = words.stream()
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .toList();

        if (nonNullWords.isEmpty()) {
            return "";
        }

        if (nonNullWords.size() == 1) {
            return nonNullWords.getFirst();
        }

        return nonNullWords.stream()
                .reduce(InterviewStreamExample::findCommonPrefix)
                .orElse("");
    }

    public String sortMethod(List<String> words) {
        if (words == null || words.isEmpty()) {
            return "";
        }

        List<String> nonNullWords = words.stream()
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .toList();

        if (nonNullWords.isEmpty()) {
            return "";
        }

        if (nonNullWords.size() == 1) {
            return nonNullWords.getFirst();
        }

        var sortedList = nonNullWords.stream()
                .sorted()
                .toList();

        return findCommonPrefix(
                sortedList.getFirst(), sortedList.getLast());
    }

    private static String findCommonPrefix(String a, String b) {

        int i = 0;
        while (i < a.length() && i < b.length() && a.charAt(i) == b.charAt(i)) {
            i++;
        }
        return a.substring(0, i);
    }
}
