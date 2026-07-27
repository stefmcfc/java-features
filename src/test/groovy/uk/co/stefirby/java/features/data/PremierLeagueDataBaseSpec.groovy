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
        expect:
        !list.isEmpty()

        where:
        accessor          | list
        "getAllPlayers()" | PremierLeagueDataBase.getAllPlayers()
        "getAllTeams()"   | PremierLeagueDataBase.getAllTeams()
        "getAllMatches()" | PremierLeagueDataBase.getAllMatches()
    }

    def "STAGE-2-AC-04b: mutating the list from #accessor throws UnsupportedOperationException"() {
        when:
        list.remove(0)

        then:
        thrown(UnsupportedOperationException)

        when:
        list.add(null)

        then:
        thrown(UnsupportedOperationException)

        where:
        accessor          | list
        "getAllPlayers()" | PremierLeagueDataBase.getAllPlayers()
        "getAllTeams()"   | PremierLeagueDataBase.getAllTeams()
        "getAllMatches()" | PremierLeagueDataBase.getAllMatches()
    }

    def "STAGE-2-AC-05: every player's team is a known team"() {
        given:
        def teamNames = PremierLeagueDataBase.getAllTeams()*.name() as Set

        expect:
        PremierLeagueDataBase.getAllPlayers().every { it.team() in teamNames }
    }

    def "STAGE-2-AC-05: every match's home and away teams are known teams"() {
        given:
        def teamNames = PremierLeagueDataBase.getAllTeams()*.name() as Set

        expect:
        PremierLeagueDataBase.getAllMatches().every {
            it.homeTeam() in teamNames && it.awayTeam() in teamNames
        }
    }

    def "STAGE-2-AC-06: dataset has at least 4 teams"() {
        expect:
        PremierLeagueDataBase.getAllTeams().size() >= 4
    }

    def "STAGE-2-AC-06: every team has at least 3 players"() {
        given:
        def playersByTeam = PremierLeagueDataBase.getAllPlayers().groupBy { it.team() }

        expect:
        PremierLeagueDataBase.getAllTeams().every { team ->
            (playersByTeam[team.name()] ?: []).size() >= 3
        }
    }
}
