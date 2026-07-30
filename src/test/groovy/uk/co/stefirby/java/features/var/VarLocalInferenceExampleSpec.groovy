package uk.co.stefirby.java.features.var

import spock.lang.Specification

/**
 * STAGE-7-AC-01: the var (10) example's PL query, run with var-declared
 * locals, shall return the same result as the explicitly-typed equivalent.
 */
class VarLocalInferenceExampleSpec extends Specification {

    def "STAGE-7-AC-01: playersWithMoreAssistsThanGoalsUsingVar returns the same result as the explicitly-typed equivalent"() {
        given: "the explicitly-typed equivalent query's result"
            def expected = VarLocalInferenceExample.playersWithMoreAssistsThanGoalsExplicit()

        when: "the var-declared equivalent is run"
            def result = VarLocalInferenceExample.playersWithMoreAssistsThanGoalsUsingVar()

        then: "the two queries return the same, non-empty result"
            result == expected
            !result.isEmpty()
    }
}
