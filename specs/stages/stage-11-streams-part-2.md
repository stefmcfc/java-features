# Stage 11: Streams Part 2

**Branch:** `feature/stage-11/streams-part-2`

## Goal

Finish the Java 9–21 Stream story the headline stage-3 pass left open: the
downstream collector combinators (`flatMapping`, `filtering`), the remaining
unmodifiable collectors and copy factories, `Predicate.not()`, and a
date-stream example — with two new streams endpoints exposing the collector
examples over HTTP.

## Scope

In: `uk.co.stefirby.java.features.streams` and
`uk.co.stefirby.java.features.collections`, plus two thin handlers added to
`uk.co.stefirby.java.features.api.StreamsController`. The
`LocalDate.datesUntil()` example lives in `streams/` per `FUTURE-23` (date
queries live where they naturally land until a dedicated `dates/` package is
wanted). The `List.copyOf()`/`Map.copyOf()` example lives in `collections/`,
completing the "immutable copies" the spec's package-layout comment promises
for that package.

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route. Ubiquitous requirement for every AC below:
each example class shall expose its feature logic as static methods, shall
provide a `main` method printing the result, and shall carry a Javadoc
header naming the JDK version; each AC is verified by a passing Spock spec.

- [ ] `STAGE-11-AC-01` [AUTO] — When the `Collectors.flatMapping()` (9)
      example groups the dataset by team with a flat-mapping downstream
      collector, it shall map each team name to the names of that team's
      goal-scorers (players with at least one goal), so Manchester City's
      entry excludes Ederson.
- [ ] `STAGE-11-AC-02` [AUTO] — When the `Collectors.filtering()` (9)
      example groups players by position keeping only those with at least a
      given number of goals, each group shall contain only qualifying
      players **and** a position whose players all fall short shall still
      appear with an empty list (e.g. `Goalkeeper` at a 1-goal threshold) —
      the distinction from filtering before grouping, which would drop the
      position entirely.
- [ ] `STAGE-11-AC-03` [AUTO] — When the `Collectors.toUnmodifiableSet()` /
      `toUnmodifiableMap()` (10) example collects the distinct nationalities
      in the league (set) and goals by player name (map), each result shall
      contain the expected entries and shall reject mutation with
      `UnsupportedOperationException`.
- [ ] `STAGE-11-AC-04` [AUTO] — When the `List.copyOf()` / `Map.copyOf()`
      (10) example takes defensive copies of a mutable squad list and
      goals-by-name map, the copies shall equal the source at the moment of
      copying, shall reject mutation with `UnsupportedOperationException`,
      and shall be unaffected by later mutation of the source.
- [ ] `STAGE-11-AC-05` [AUTO] — When the `Predicate.not()` (11) example
      filters the dataset to outfield players via
      `Predicate.not(<goalkeeper test>)`, it shall return exactly the
      players whose position is not `Goalkeeper`, matching the equivalent
      negated-lambda filter.
- [ ] `STAGE-11-AC-06` [AUTO] — When the `LocalDate.datesUntil()` (9)
      example streams every Saturday of the season week by week from the
      first match date to just past the last, the resulting dates shall
      include every match date in the dataset (all fixtures sit on the
      weekly grid from matchweek 1).
- [ ] `STAGE-11-AC-07` [AUTO] — When `GET /api/streams/scorers-by-team` is
      requested, the API shall respond 200 with the flat-mapping example's
      team-to-scorer-names result as JSON, delegating straight to the
      static method (thin-controller rule).
- [ ] `STAGE-11-AC-08` [AUTO] — When
      `GET /api/streams/players-by-position?minGoals=<n>` is requested with
      a non-negative `minGoals`, the API shall respond 200 with the
      filtering example's result for that threshold as JSON; where
      `minGoals` is omitted, the API shall default it to `0`.
- [ ] `STAGE-11-AC-09` [AUTO] — If `minGoals` is negative, then the API
      shall respond 400 with an RFC 9457 `ProblemDetail` body.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
