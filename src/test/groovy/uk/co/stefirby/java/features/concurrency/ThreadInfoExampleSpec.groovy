package uk.co.stefirby.java.features.concurrency

import spock.lang.Specification

import java.util.concurrent.atomic.AtomicReference

/**
 * STAGE-9-AC-03 (unit level): the concurrency static method behind the
 * thread-info endpoint reports the executing thread's name and virtual-ness
 * via Thread::isVirtual, so the controller spec can prove the API serves
 * requests on virtual threads.
 */
class ThreadInfoExampleSpec extends Specification {

    def "STAGE-9-AC-03: currentThreadReport reports a platform thread as not virtual"() {
        when: "the thread report is taken on the (platform) test thread"
            def report = ThreadInfoExample.currentThreadReport()

        then: "the report carries the current thread's name and virtual=false"
            report.threadName() == Thread.currentThread().getName()
            !report.virtual()
    }

    def "STAGE-9-AC-03: currentThreadReport reports a virtual thread as virtual"() {
        given: "a holder for the report taken inside a virtual thread"
            def captured = new AtomicReference()

        when: "the thread report is taken on a virtual thread"
            Thread.ofVirtual().name("stage-9-virtual").start {
                captured.set(ThreadInfoExample.currentThreadReport())
            }.join()

        then: "the report carries the virtual thread's name and virtual=true"
            captured.get().threadName() == "stage-9-virtual"
            captured.get().virtual()
    }
}
