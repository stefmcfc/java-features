# Stage 7: Records and Language Basics

**Branch:** `feature/stage-7/records-and-basics`

## Goal

Cover `var`, text blocks, and records, and extend the API layer with a
records-backed endpoint.

## Scope

In: `uk.co.stefirby.java.features.var`, `uk.co.stefirby.java.features.text_blocks`, `uk.co.stefirby.java.features.records`,
one controller in `uk.co.stefirby.java.features.api`.

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route. Ubiquitous requirement for every example AC
below: each example class shall expose its feature logic as static methods,
shall provide a `main` method printing the result, and shall carry a Javadoc
header naming the JDK version; each AC is verified by a passing Spock spec.

- [x] `STAGE-7-AC-01` [AUTO] — When the `var` (10) example runs its PL
      query with `var`-declared locals, it shall return the same result as
      the explicitly-typed equivalent.
- [x] `STAGE-7-AC-02` [AUTO] — When the text-blocks (15) example builds a
      multi-line matchday report, it shall produce exactly the expected
      line structure and content.
- [x] `STAGE-7-AC-03a` [AUTO] — The records (16) example shall define a
      record whose compact constructor validates its components.
- [x] `STAGE-7-AC-03b` [AUTO] — If invalid component values are supplied
      (e.g. negative goals), then record construction shall throw
      `IllegalArgumentException`.
- [x] `STAGE-7-AC-04a` [AUTO] — When `GET /api/records/player/{id}` is
      requested with a known id, the API shall respond 200 with a
      record-based DTO as JSON.
- [x] `STAGE-7-AC-04b` [AUTO] — If an unknown id is requested, then the API
      shall respond 404 with a `ProblemDetail` body (consistent with
      `STAGE-5-AC-05b`).

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
