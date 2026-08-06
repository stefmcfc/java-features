package uk.co.stefirby.java.features.collections

import spock.lang.Specification

/**
 * Covers STAGE-11-AC-04 — List.copyOf() / Map.copyOf() (10) take defensive
 * copies of a mutable squad list and goals-by-name map: the copies equal
 * the source at the moment of copying, reject mutation, and are unaffected
 * by later mutation of the source.
 */
class ImmutableCopyExampleSpec extends Specification {

    def "STAGE-11-AC-04: squadCopy equals the source squad at the moment of copying"() {
        given: "a mutable squad list"
            def squad = ImmutableCopyExample.mutableSquad()

        when: "squadCopy is called"
            def copy = ImmutableCopyExample.squadCopy(squad)

        then: "the copy equals the source"
            copy == squad
    }

    def "STAGE-11-AC-04: the squad copy rejects mutation"() {
        given: "a copy of the mutable squad"
            def copy = ImmutableCopyExample.squadCopy(ImmutableCopyExample.mutableSquad())

        when: "a mutation is attempted"
            copy.add(copy.first())

        then: "the copy rejects the mutation"
            thrown(UnsupportedOperationException)
    }

    def "STAGE-11-AC-04: the squad copy is unaffected by later mutation of the source"() {
        given: "a mutable squad and its copy"
            def squad = ImmutableCopyExample.mutableSquad()
            def copy = ImmutableCopyExample.squadCopy(squad)
            def originalCopy = List.copyOf(copy)

        when: "the source squad is later mutated"
            squad.add(squad.first())

        then: "the copy is unaffected"
            copy == originalCopy
    }

    def "STAGE-11-AC-04: goalsByNameCopy equals the source map at the moment of copying"() {
        given: "a mutable goals-by-name map"
            def goalsByName = ImmutableCopyExample.mutableGoalsByName()

        when: "goalsByNameCopy is called"
            def copy = ImmutableCopyExample.goalsByNameCopy(goalsByName)

        then: "the copy equals the source"
            copy == goalsByName
    }

    def "STAGE-11-AC-04: the goals-by-name copy rejects mutation"() {
        given: "a copy of the mutable goals-by-name map"
            def copy = ImmutableCopyExample.goalsByNameCopy(ImmutableCopyExample.mutableGoalsByName())

        when: "a mutation is attempted"
            copy.put("Nobody", 0)

        then: "the copy rejects the mutation"
            thrown(UnsupportedOperationException)
    }

    def "STAGE-11-AC-04: the goals-by-name copy is unaffected by later mutation of the source"() {
        given: "a mutable goals-by-name map and its copy"
            def goalsByName = ImmutableCopyExample.mutableGoalsByName()
            def copy = ImmutableCopyExample.goalsByNameCopy(goalsByName)
            def originalCopy = Map.copyOf(copy)

        when: "the source map is later mutated"
            goalsByName.put("Nobody", 0)

        then: "the copy is unaffected"
            copy == originalCopy
    }
}
