# Stage 8: Sealed Classes and Pattern Matching

**Branch:** `feature/stage-8/sealed-and-pattern-matching`

## Goal

Cover sealed types, pattern matching (`instanceof` and `switch`), record
deconstruction, and enhanced switch expressions.

## Scope

In: `uk.co.stefirby.java.features.sealed`, `uk.co.stefirby.java.features.pattern_matching`,
`uk.co.stefirby.java.features.switch_expressions`.

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route. Ubiquitous requirement for every AC below:
each example class shall expose its feature logic as static methods, shall
provide a `main` method printing the result, and shall carry a Javadoc
header naming the JDK version; each AC is verified by a passing Spock spec.

- [x] `STAGE-8-AC-01` [AUTO] — The sealed types (17) example shall model
      match outcomes as a sealed hierarchy permitting exactly a fixed set
      of subtypes (e.g. `HomeWin`, `AwayWin`, `Draw`); the spec shall
      assert the permitted subtypes.
- [x] `STAGE-8-AC-02` [AUTO] — When the `instanceof` pattern-matching (16)
      example classifies an object that may be a `Player`, `Team`, or
      `Match`, it shall describe it via pattern variables with no explicit
      casts.
- [x] `STAGE-8-AC-03` [AUTO] — When the `switch` pattern-matching (21)
      example is given a match outcome, it shall produce a summary via
      record deconstruction patterns, covering all sealed cases without a
      `default` branch.
- [x] `STAGE-8-AC-04` [AUTO] — When the arrow-`switch` (14) example maps a
      player's position to a category, it shall use `switch` as an
      expression with at least one multi-statement branch using `yield`.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
