# Stage 12: Async and Simulation

**Branch:** `feature/stage-12/async-and-simulation`

## Goal

Cover the asynchronous halves of APIs already in the project —
`HttpClient.sendAsync()`, the Java 9 `CompletableFuture` timeout additions,
and the Java 19 `Future` state inspectors — plus the Java 17
`RandomGenerator` API driving a seeded match simulator behind a new
endpoint, and the remaining Java 12/15 `String` helpers.

## Scope

In: `uk.co.stefirby.java.features.httpclient` (async client),
`uk.co.stefirby.java.features.concurrency` (`CompletableFuture`/`Future`
examples), `uk.co.stefirby.java.features.text_blocks` (`String` helpers,
co-located per the stage-10 precedent), and a new
`uk.co.stefirby.java.features.random` package for the `RandomGenerator`
simulator, plus one thin controller in `uk.co.stefirby.java.features.api`.

Adding `random/` extends the package layout: update the hard-rule package
list in `CLAUDE.md` and the main spec's Proposed Package Layout as part of
this stage (per `specs/future-development.md`'s promotion rule). As in
stage 10, the async client example takes the base URI as a parameter rather
than hardcoding `localhost`, so it stays runnable standalone from `main`
against any running instance.

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route. Ubiquitous requirement for every AC below:
each example class shall expose its feature logic as static methods, shall
provide a `main` method printing the result, and shall carry a Javadoc
header naming the JDK version; each AC is verified by a passing Spock spec.

- [x] `STAGE-12-AC-01` [AUTO] — When the `HttpClient.sendAsync()` (11)
      example issues an asynchronous GET (suggested target: the app's own
      stage-4 top-scorers endpoint), it shall return a `CompletableFuture`
      that completes with the response status and body, without blocking
      the calling thread — delivering the asynchronous request style the
      `httpclient` package Javadoc has promised since stage 1.
- [x] `STAGE-12-AC-02` [AUTO] — When the `CompletableFuture` timeout
      additions (9) example runs a dataset query that outlives its
      deadline, `orTimeout()` shall complete the future exceptionally with
      a `TimeoutException` and `completeOnTimeout()` shall complete it with
      the given fallback value; a query that finishes in time shall
      complete normally with its result.
- [x] `STAGE-12-AC-03` [AUTO] — When the `Future.state()` /
      `resultNow()` / `exceptionNow()` (19) example inspects two completed
      dataset queries, it shall report `SUCCESS` with the result via
      `resultNow()` for the successful one and `FAILED` with the cause via
      `exceptionNow()` for the failed one.
- [x] `STAGE-12-AC-04` [AUTO] — When the `RandomGenerator` (17) example
      simulates a matchweek of fixtures from the dataset's teams using a
      seeded generator obtained via `RandomGenerator.of(...)` /
      `RandomGeneratorFactory`, the same seed shall always produce the
      same scorelines, and every simulated score shall be clamped to a
      plausible range via `Math.clamp()` (21).
- [x] `STAGE-12-AC-05` [AUTO] — When the `String.transform()` /
      `indent()` (12) and `formatted()` (15) example builds a report over
      PL text data, each of the three methods shall produce its asserted
      result.
- [x] `STAGE-12-AC-06` [AUTO] — When
      `GET /api/random/simulated-matchweek?seed=<n>` is requested, the API
      shall respond 200 with the simulator's fixtures for that seed as
      JSON, including the seed in the payload, and repeated requests with
      the same seed shall return identical bodies.
- [x] `STAGE-12-AC-07` [AUTO] — Where `seed` is omitted, the API shall
      pick a seed itself and include it in the payload, so any simulation
      can be reproduced by requesting that seed explicitly.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
