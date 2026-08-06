package uk.co.stefirby.java.features.text_blocks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import uk.co.stefirby.java.features.data.Match;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 11: {@link Files#writeString} / {@link Files#readString} — one-call,
 * UTF-8-by-default file I/O for whole strings, with no reader/writer or byte
 * ceremony. Round-trips the stage-7 text-block matchday report through a
 * file and back.
 */
public class FilesReadWriteStringExample {

    /**
     * @param target the file to write the report to
     * @param match  the fixture to report on
     * @return the path written, as returned by {@code Files.writeString}
     */
    public static Path writeMatchdayReport(Path target, Match match) throws IOException {
        return Files.writeString(target, MatchdayReportExample.matchdayReport(match));
    }

    /**
     * @param target the file a report was written to
     * @return the report read back in full, per {@code Files.readString}
     */
    public static String readMatchdayReport(Path target) throws IOException {
        return Files.readString(target);
    }

    public static void main(String[] args) throws IOException {
        Path target = Files.createTempFile("matchday-report", ".txt");
        Match match = PremierLeagueDataBase.getAllMatches().getFirst();
        writeMatchdayReport(target, match);
        System.out.println(readMatchdayReport(target));
    }
}
