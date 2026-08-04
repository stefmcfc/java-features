# Stage 9: Concurrency

**Branch:** `feature/stage-9/concurrency`

## Goal

Cover virtual threads and structured concurrency, and show the production
payoff by running the Spring Boot HTTP layer itself on virtual threads.

## Scope

In: `uk.co.stefirby.java.features.concurrency`, one thin controller in
`uk.co.stefirby.java.features.api`. The structured-concurrency example is the
only class in the project requiring `--enable-preview`; contain that flag's
blast radius to just that one class (a dedicated Gradle source set or
per-file compile task) rather than enabling preview features project-wide.

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route. Ubiquitous requirement for every AC below:
each example class shall expose its feature logic as static methods, shall
provide a `main` method printing the result, and shall carry a Javadoc
header naming the JDK version; each AC is verified by a passing Spock spec.

- [x] `STAGE-9-AC-01` [AUTO] — When the virtual-threads (21) example runs
      its concurrent PL queries, every task shall execute on a virtual
      thread (asserted via `Thread::isVirtual`).
- [x] `STAGE-9-AC-02` [AUTO] — Where the build enables the preview flag the
      target JDK requires, the structured-concurrency example shall run
      two dataset queries as subtasks of one scope that succeeds or fails
      as a unit; its Javadoc header shall explicitly flag the API as
      preview.
- [x] `STAGE-9-AC-03` [AUTO] — While `spring.threads.virtual.enabled=true`,
      when the thread-info endpoint is requested, the API shall serve the
      request on a virtual thread — the thread report comes from a
      `concurrency` static method behind a thin controller, and an
      integration Spock spec asserts the virtual-ness. Demonstrates the
      one-property production payoff of Loom in Boot 4.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
