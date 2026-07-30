package uk.co.stefirby.java.features.streams

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class InterviewStreamExampleSpec extends Specification {

    private static final List<String> NULL = null
    private static final List<String> EMPTY = Collections.emptyList()
    private static final List<String> ONE_ENTRY = List.of("one")
    private static final List<String> LIST_CONTAINS_NULL = new ArrayList<>()
    private static final List<String> MULTIPLE_ENTRIES = List.of("star", "starch", "stardust", "stare", "stark")

    @Subject
    InterviewStreamExample testClass

    def setup() {
        testClass = new InterviewStreamExample()
        LIST_CONTAINS_NULL.add("I'm not null!")
        LIST_CONTAINS_NULL.add(null)
    }

    @Unroll
    def "when commonPrefix is called with #scenario then result is expected"() {
        given: "commonPrefix is called with the input list"
            var result = testClass.commonPrefix(inputList)

        expect: "the result is the expected common prefix"
            result == expectedResult

        where:
            scenario               | inputList          | expectedResult
            "null"                 | NULL               | ""
            "empty list"           | EMPTY               | ""
            "one non-null entry"   | ONE_ENTRY          | "one"
            "list containing null" | LIST_CONTAINS_NULL | "I'm not null!"
            "multiple entries"     | MULTIPLE_ENTRIES   | "star"
    }

    @Unroll
    def "when sortMethod is called with #scenario then result is expected"() {
        given: "sortMethod is called with the input list"
            var result = testClass.sortMethod(inputList)

        expect: "the result is the expected value"
            result == expectedResult

        where:
            scenario               | inputList          | expectedResult
            "null"                 | NULL               | ""
            "empty list"           | EMPTY               | ""
            "one non-null entry"   | ONE_ENTRY          | "one"
            "list containing null" | LIST_CONTAINS_NULL | "I'm not null!"
            "multiple entries"     | MULTIPLE_ENTRIES   | "star"
    }
}
