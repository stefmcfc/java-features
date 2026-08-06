package uk.co.stefirby.java.features.concurrency

import spock.lang.Specification

import java.util.concurrent.Future

/**
 * STAGE-12-AC-03: when the Future.state()/resultNow()/exceptionNow() (19)
 * example inspects two completed dataset queries, it reports SUCCESS with
 * the result via resultNow() for the successful one and FAILED with the
 * cause via exceptionNow() for the failed one.
 */
class FutureStateExampleSpec extends Specification {

    def "STAGE-12-AC-03: a successful query reports SUCCESS with its result via resultNow()"() {
        when: "the example inspects a successful dataset query"
            def outcome = FutureStateExample.inspectSuccessful()

        then: "the state is SUCCESS"
            outcome.state() == Future.State.SUCCESS

        and: "the description carries the result obtained via resultNow()"
            outcome.description().contains("team count")
    }

    def "STAGE-12-AC-03: a failed query reports FAILED with its cause via exceptionNow()"() {
        when: "the example inspects a failed dataset query"
            def outcome = FutureStateExample.inspectFailed()

        then: "the state is FAILED"
            outcome.state() == Future.State.FAILED

        and: "the description carries the cause's message obtained via exceptionNow()"
            outcome.description().contains("dataset query failed")
    }
}
