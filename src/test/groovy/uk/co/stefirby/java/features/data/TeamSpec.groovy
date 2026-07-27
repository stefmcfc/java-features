package uk.co.stefirby.java.features.data

import spock.lang.Specification

/**
 * STAGE-2-AC-02: the data.Team record shall expose name, stadium, manager,
 * and founded year as components.
 */
class TeamSpec extends Specification {

    def "Team is a record"() {
        expect:
        Team.isRecord()
    }

    def "Team exposes the expected components in order"() {
        expect:
        Team.recordComponents*.name == ["name", "stadium", "manager", "founded"]
    }

    def "Team components are readable through their accessors"() {
        given:
        def team = new Team("Arsenal", "Emirates Stadium", "Mikel Arteta", 1886)

        expect:
        team.name() == "Arsenal"
        team.stadium() == "Emirates Stadium"
        team.manager() == "Mikel Arteta"
        team.founded() == 1886
    }
}
