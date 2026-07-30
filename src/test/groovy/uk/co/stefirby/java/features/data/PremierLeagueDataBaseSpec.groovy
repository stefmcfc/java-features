package uk.co.stefirby.java.features.data

import spock.lang.Specification

/**
 * Covers the in-memory dataset ACs:
 * <ul>
 *   <li>STAGE-2-AC-04a — getAllPlayers()/getAllTeams()/getAllMatches()
 *       return non-empty, purely in-memory lists.</li>
 *   <li>STAGE-2-AC-04b — returned lists reject mutation with
 *       UnsupportedOperationException.</li>
 *   <li>STAGE-2-AC-05 — every Player.team and every Match home/away team
 *       matches a name present in getAllTeams().</li>
 *   <li>STAGE-2-AC-06 — at least 4 teams and at least 3 players per team.</li>
 * </ul>
 */
class PremierLeagueDataBaseSpec extends Specification {

    def "STAGE-2-AC-04a: #accessor returns a non-empty list"() {
        expect: "the accessor's result is not empty"
            !list.isEmpty()

        where:
            accessor          | list
            "getAllPlayers()" | PremierLeagueDataBase.getAllPlayers()
            "getAllTeams()"   | PremierLeagueDataBase.getAllTeams()
            "getAllMatches()" | PremierLeagueDataBase.getAllMatches()
    }

    def "STAGE-2-AC-04b: mutating the list from #accessor throws UnsupportedOperationException"() {
        when: "an element is removed"
            list.remove(0)

        then: "the list rejects the mutation"
            thrown(UnsupportedOperationException)

        when: "an element is added"
            list.add(null)

        then: "the list rejects the mutation"
            thrown(UnsupportedOperationException)

        where:
            accessor          | list
            "getAllPlayers()" | PremierLeagueDataBase.getAllPlayers()
            "getAllTeams()"   | PremierLeagueDataBase.getAllTeams()
            "getAllMatches()" | PremierLeagueDataBase.getAllMatches()
    }

    def "STAGE-2-AC-05: every player's team is a known team"() {
        given: "the set of known team names"
            def teamNames = PremierLeagueDataBase.getAllTeams()*.name() as Set

        expect: "every player's team is in that set"
            PremierLeagueDataBase.getAllPlayers().every { it.team() in teamNames }
    }

    def "STAGE-2-AC-05: every match's home and away teams are known teams"() {
        given: "the set of known team names"
            def teamNames = PremierLeagueDataBase.getAllTeams()*.name() as Set

        expect: "every match's home and away teams are in that set"
            PremierLeagueDataBase.getAllMatches().every {
                it.homeTeam() in teamNames && it.awayTeam() in teamNames
            }
    }

    def "STAGE-2-AC-06: dataset has at least 4 teams"() {
        expect: "getAllTeams() has at least 4 entries"
            PremierLeagueDataBase.getAllTeams().size() >= 4
    }

    def "STAGE-2-AC-06: every team has at least 3 players"() {
        given: "players grouped by team"
            def playersByTeam = PremierLeagueDataBase.getAllPlayers().groupBy { it.team() }

        expect: "every team has at least 3 players in that grouping"
            PremierLeagueDataBase.getAllTeams().every { team ->
                (playersByTeam[team.name()] ?: []).size() >= 3
            }
    }
}
