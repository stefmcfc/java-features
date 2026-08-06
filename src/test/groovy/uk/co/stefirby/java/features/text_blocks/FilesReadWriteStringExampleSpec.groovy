package uk.co.stefirby.java.features.text_blocks

import spock.lang.Specification
import spock.lang.TempDir
import uk.co.stefirby.java.features.data.Match

import java.nio.file.Path
import java.time.LocalDate

/**
 * STAGE-10-AC-03: when the Files.writeString()/readString() (11) example
 * writes a matchday report to a temporary file and reads it back, the
 * content shall round-trip identically.
 */
class FilesReadWriteStringExampleSpec extends Specification {

    @TempDir
    Path tempDir

    def "STAGE-10-AC-03: a matchday report round-trips identically through writeString and readString"() {
        given: "a known match and a temporary file to report into"
            def match = new Match("Liverpool", "Tottenham Hotspur", 3, 1, LocalDate.of(2025, 8, 23))
            def target = tempDir.resolve("matchday-report.txt")

        when: "the example writes the matchday report to the temporary file and reads it back"
            FilesReadWriteStringExample.writeMatchdayReport(target, match)
            def readBack = FilesReadWriteStringExample.readMatchdayReport(target)

        then: "the content round-trips identically"
            readBack == MatchdayReportExample.matchdayReport(match)
    }
}
