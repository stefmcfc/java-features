# Stage 6: Collections

**Branch:** `feature/stage-6/collections`

## Goal

Cover the immutable factory methods and Sequenced Collections additions.

## Scope

In: `uk.co.stefirby.java.features.collections`.

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route. Ubiquitous requirement for every AC below:
each example class shall expose its feature logic as static methods, shall
provide a `main` method printing the result, and shall carry a Javadoc
header naming the JDK version; each AC is verified by a passing Spock spec.

- [ ] `STAGE-6-AC-01a` [AUTO] — When the immutable-factories (9) example is
      invoked, it shall build its collections over PL data with
      `List.of()`, `Set.of()`, and `Map.of()`.
- [ ] `STAGE-6-AC-01b` [AUTO] — If a mutation is attempted on any
      collection returned by the immutable-factories example, then
      `UnsupportedOperationException` shall be thrown.
- [ ] `STAGE-6-AC-02` [AUTO] — When the Sequenced Collections (21) example
      is applied to an ordered PL dataset list, `getFirst()` shall return
      the first element, `getLast()` the last, and `reversed()` a view in
      opposite order.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
