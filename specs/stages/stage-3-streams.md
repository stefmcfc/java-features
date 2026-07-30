# Stage 3: Streams (headline focus)

**Branch:** `feature/stage-3/streams`

## Goal

Implement the headline Streams feature set against the PL dataset from
stage 2. This is the stated priority of the whole project — get it right
before branching out.

## Scope

In: `uk.co.stefirby.java.features.streams`. Out: exposing any of this over HTTP (stage 4).

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route. Ubiquitous requirement for every AC below:
each example class shall expose its feature logic as static methods, shall
provide a `main` method printing the result, and shall carry a Javadoc
header naming the JDK version; each AC is verified by a passing Spock spec.

- [x] `STAGE-3-AC-01` [AUTO] — When the `Stream.toList()` (16) example is
      invoked, it shall return the players' names sorted by goals
      descending as an unmodifiable list; if the list is mutated, then
      `UnsupportedOperationException` shall be thrown.
- [x] `STAGE-3-AC-02` [AUTO] — When the `Stream.mapMulti()` (16) example is
      invoked with the PL teams, it shall flatten each team into its
      players' names via `mapMulti`.
- [x] `STAGE-3-AC-03` [AUTO] — When the `Collectors.teeing()` (12) example
      is invoked, it shall compute the league's total goals and average
      goals per player in a single pass and return both together.
- [x] `STAGE-3-AC-04` [AUTO] — When the `takeWhile()` / `dropWhile()` (9)
      example is applied to players sorted by goals descending,
      `takeWhile` shall return only the leading players above a goals
      threshold, and `dropWhile` shall return exactly the remainder.
- [x] `STAGE-3-AC-05` [AUTO] — When the `Stream.iterate()` predicate
      overload (9) example is invoked, it shall generate the matchweek
      numbers 1 through 38 without an explicit `limit()` call.
- [x] `STAGE-3-AC-06` [AUTO] — When the `Stream.ofNullable()` (9) example
      is given a null lookup result, it shall yield an empty stream; when
      given a non-null player, it shall yield exactly that one element.
- [x] `STAGE-3-AC-07` [AUTO] — When the sequenced-collection stream view
      (21) example is invoked, it shall stream the teams via `reversed()`
      and return them last-to-first.
- [x] `STAGE-3-AC-08` [AUTO] — When the players-grouped-by-team example is
      invoked, it shall group all PL players by team name via
      `Collectors.groupingBy`, with each team's players collected into an
      unmodifiable list via `Collectors.toUnmodifiableList()` (10), and
      return a map keyed by team name — the endpoint stage 4's
      `STAGE-4-AC-02` (`GET /api/streams/grouped-by-team`) delegates to.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
