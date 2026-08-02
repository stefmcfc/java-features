package uk.co.stefirby.java.features.switch_expressions

import spock.lang.Specification

/**
 * STAGE-8-AC-04: when the arrow-switch (14) example maps a player's
 * position to a category, it uses switch as an expression with at least
 * one multi-statement branch using yield.
 */
class ArrowSwitchExampleSpec extends Specification {

    def "STAGE-8-AC-04: categorise maps each known position to its category"() {
        expect: "the switch expression yields the category for the position"
            ArrowSwitchExample.categorise(position) == category

        where:
            position     | category
            "Goalkeeper" | "Defence"
            "Defender"   | "Defence"
            "Midfielder" | "Midfield"
            "Forward"    | "Attack"
    }

    def "STAGE-8-AC-04: categorise handles an unknown position via the multi-statement yield branch"() {
        when: "an unknown position is categorised"
            def category = ArrowSwitchExample.categorise("  Wing-back  ")

        then: "the multi-statement branch normalises the position and yields a fallback"
            category == "Uncategorised: Wing-back"
    }
}
