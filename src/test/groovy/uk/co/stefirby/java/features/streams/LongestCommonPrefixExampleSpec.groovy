package uk.co.stefirby.java.features.streams

import spock.lang.Specification
import spock.lang.Unroll
import uk.co.stefirby.java.features.data.PremierLeagueDataBase

/**
 * Covers the longest-common-prefix interview exercise, tailored to the
 * Premier League dataset's team names: both strategies (pairwise reduction,
 * sort-then-compare-extremes) must return the same prefix for every
 * scenario, including null/blank-heavy edge cases.
 */
class LongestCommonPrefixExampleSpec extends Specification {

    @Unroll
    def "commonPrefixByReduction returns '#expectedPrefix' for #scenario"() {
        when: "commonPrefixByReduction is called with the team names"
            def result = LongestCommonPrefixExample.commonPrefixByReduction(teamNames)

        then: "the result is the expected common prefix"
            result == expectedPrefix

        where:
            scenario                             | teamNames                                          | expectedPrefix
            "a null list"                        | null                                               | ""
            "an empty list"                      | []                                                 | ""
            "a single team"                      | ["Arsenal"]                                        | "Arsenal"
            "a list containing a null entry"     | ["Manchester City", null]                          | "Manchester City"
            "a list of only null entries"        | [null, null]                                       | ""
            "the two Manchester clubs"           | ["Manchester City", "Manchester United"]           | "Manchester "
            "clubs with no shared prefix"        | ["Arsenal", "Chelsea", "Liverpool"]                | ""
            "a name that is a prefix of another" | ["Tottenham Hotspur", "Tottenham Hotspur Stadium"] | "Tottenham Hotspur"
            "identical names"                    | ["Liverpool", "Liverpool"]                         | "Liverpool"
            "names differing only in case"       | ["arsenal", "Arsenal"]                             | ""
            "a blank entry among names"          | ["   ", "Manchester City", "Manchester United"]    | "Manchester "
    }

    @Unroll
    def "commonPrefixBySorting returns '#expectedPrefix' for #scenario"() {
        when: "commonPrefixBySorting is called with the team names"
            def result = LongestCommonPrefixExample.commonPrefixBySorting(teamNames)

        then: "the result is the expected common prefix"
            result == expectedPrefix

        where:
            scenario                             | teamNames                                          | expectedPrefix
            "a null list"                        | null                                               | ""
            "an empty list"                      | []                                                 | ""
            "a single team"                      | ["Arsenal"]                                        | "Arsenal"
            "a list containing a null entry"     | ["Manchester City", null]                          | "Manchester City"
            "a list of only null entries"        | [null, null]                                       | ""
            "the two Manchester clubs"           | ["Manchester City", "Manchester United"]           | "Manchester "
            "clubs with no shared prefix"        | ["Arsenal", "Chelsea", "Liverpool"]                | ""
            "a name that is a prefix of another" | ["Tottenham Hotspur", "Tottenham Hotspur Stadium"] | "Tottenham Hotspur"
            "identical names"                    | ["Liverpool", "Liverpool"]                         | "Liverpool"
            "names differing only in case"       | ["arsenal", "Arsenal"]                             | ""
            "a blank entry among names"          | ["   ", "Manchester City", "Manchester United"]    | "Manchester "
    }

    def "both strategies agree across the full team dataset"() {
        given: "every team name in the dataset"
            def allTeamNames = PremierLeagueDataBase.getAllTeams()*.name()

        expect: "the strategies return the same (empty) prefix"
            LongestCommonPrefixExample.commonPrefixByReduction(allTeamNames) ==
                    LongestCommonPrefixExample.commonPrefixBySorting(allTeamNames)
    }
}
