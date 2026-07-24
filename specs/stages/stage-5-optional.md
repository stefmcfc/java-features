# Stage 5: Optional

**Branch:** `feature/stage-5/optional`

## Goal

Cover the `Optional` additions from Java 9-11, and expose one of them over
HTTP to extend the API layer proven in stage 4.

## Scope

In: `uk.co.stefirby.java.features.optional`, one controller in `uk.co.stefirby.java.features.api`.

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route. Ubiquitous requirement for every example AC
below: each example class shall expose its feature logic as static methods,
shall provide a `main` method printing the result, and shall carry a Javadoc
header naming the JDK version; each AC is verified by a passing Spock spec.

- [ ] `STAGE-5-AC-01` [AUTO] — When the `Optional.or()` (9) example looks
      up the top scorer of a team with no players, it shall fall back via
      `or()` to an alternative supplier (e.g. the league-wide top scorer).
- [ ] `STAGE-5-AC-02` [AUTO] — When the `Optional.ifPresentOrElse()` (9)
      example resolves a present player, it shall run the present action;
      if the lookup is empty, then it shall run the empty action instead.
- [ ] `STAGE-5-AC-03` [AUTO] — When the `Optional.isEmpty()` (11) example
      queries a team with no players, it shall report `true`; for a
      populated team it shall report `false`.
- [ ] `STAGE-5-AC-04` [AUTO] — When the `Optional.stream()` (9) example
      flattens several teams' top-scorer lookups, it shall yield a stream
      containing only the present players, with empties dropped.
- [ ] `STAGE-5-AC-05a` [AUTO] — When
      `GET /api/optional/team-top-scorer?team=<known>` is requested, the
      API shall respond 200 with that team's top scorer as JSON, backed by
      the `Optional<Player>`-returning example.
- [ ] `STAGE-5-AC-05b` [AUTO] — If an unknown team is requested, then the
      API shall respond 404 with a `ProblemDetail` (RFC 9457) body —
      Spring's idiomatic REST error shape — asserted for both status and
      problem content.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
