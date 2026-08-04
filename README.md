# Modern Java Features

## Overview

A learning/reference project demonstrating Java language and API features
introduced from Java 9 through Java 21 (Java 22–25 is a planned follow-on,
see `specs/future-development.md`). Full spec: `specs/modern-java-features-spec.md`.
Stage-by-stage breakdown: `specs/stages/`.

## Prerequisites

- JDK 21 (installed and selected manually — the build does not auto-provision
  a toolchain)
- No other tooling required; Gradle is invoked via the wrapper (`gradlew`/`gradlew.bat`)

## Getting Started

Run once per clone to enable the pre-commit test gate:

```
git config core.hooksPath githooks
```

## Project Structure

```
uk.co.stefirby.java.features
├── streams/            // Stream API additions post-Java 8
├── collections/        // Sequenced Collections, List.of/Set.of/Map.of
├── records/             // Records, record patterns
├── sealed/              // Sealed classes/interfaces
├── pattern_matching/    // instanceof / switch pattern matching
├── switch_expressions/  // Arrow switch, yield
├── text_blocks/         // Text blocks
├── var/                 // Local variable type inference
├── optional/            // Optional additions
├── concurrency/         // Virtual threads, structured concurrency
├── httpclient/          // java.net.http.HttpClient
├── data/                // Shared Premier League domain model + in-memory dataset
├── api/                 // Spring Boot REST controllers exposing selected examples
└── qAnda/               // Practice exercises, one per topic
```

The `data/` package carries the shared dataset (stage 2): `Player`,
`Team`, and `Match` records plus `PremierLeagueDataBase`, whose static
`getAllPlayers()`/`getAllTeams()`/`getAllMatches()` return immutable
in-memory lists (6 teams, 22 players, 10 matches). The `streams/` package
carries the stage-3 examples (see Feature Coverage below). The
`collections/` package carries the stage-6 collection-API examples. The
`optional/` package carries the stage-5 `java.util.Optional` examples. The
`var/`, `text_blocks/`, and `records/` packages carry the stage-7 language-
basics examples. The `sealed/`, `pattern_matching/`, and
`switch_expressions/` packages carry the stage-8 sealed-types and
pattern-matching examples. The `concurrency/` package carries the stage-9
virtual-threads examples; its structured-concurrency example lives in the
dedicated `src/preview/java` source set (see Feature Coverage below). The
`api/` package carries the REST layer:
`StreamsController` (stage 4) exposes `GET /api/streams/top-scorers` and
`GET /api/streams/grouped-by-team`; `OptionalController` (stage 5) exposes
`GET /api/optional/team-top-scorer`; `RecordsController` (stage 7) exposes
`GET /api/records/player/{id}`; `ConcurrencyController` (stage 9) exposes
`GET /api/concurrency/thread-info`. Every handler delegates straight to a
static method in the matching topic package. The remaining packages still
hold only a `package-info.java` scaffold and fill in over the later stages.

## Running the Examples

Every example class has a `main` method printing its result. Build once,
then run any class directly, e.g.:

```
./gradlew build
java -cp build/classes/java/main uk.co.stefirby.java.features.streams.StreamToListExample
java -cp build/classes/java/main uk.co.stefirby.java.features.data.PremierLeagueDataBase
```

The one exception is the structured-concurrency example, which uses a
preview API and therefore needs the preview source set's output and the
`--enable-preview` runtime flag:

```
java --enable-preview -cp "build/classes/java/preview;build/classes/java/main" uk.co.stefirby.java.features.concurrency.StructuredConcurrencyExample
```

(Use `:` instead of `;` as the classpath separator on macOS/Linux.)

The minimal Spring Boot entry point is
`uk.co.stefirby.java.features.JavaFeaturesApplication`.

## Running the REST API

```
./gradlew bootRun
```

Boots the Spring Boot 4.1 application on port 8080. Stage 4 wires up:

- `GET /api/streams/top-scorers`
- `GET /api/streams/grouped-by-team`
- `GET /v3/api-docs` — the generated OpenAPI 3.x document (via
  springdoc-openapi), which tracks every controller in `api/` automatically
- `/swagger-ui/index.html` — the Swagger UI over that document

Stage 5 adds:

- `GET /api/optional/team-top-scorer?team=<name>` — 200 with that team's top
  scorer as JSON, or 404 with an RFC 9457 `ProblemDetail` body for an
  unknown team

Stage 7 adds:

- `GET /api/records/player/{id}` — 200 with the player at that position in
  the dataset as a `PlayerSummary` record DTO, or 404 with an RFC 9457
  `ProblemDetail` body for an unknown id

Stage 9 adds:

- `GET /api/concurrency/thread-info` — the serving thread's name and
  virtual-ness; with `spring.threads.virtual.enabled=true` set in
  `application.properties`, the report shows every request being handled on
  a virtual thread

## Testing

```
./gradlew test
```

Spock (Groovy) specs live under `src/test/groovy/...`, one `*Spec.groovy`
per example class. The JaCoCo coverage gate is intentionally disabled until
a later stage introduces real logic to measure (see `FUTURE-25`).

## Feature Coverage

**Streams (stage 3, headline focus)** — one example class per feature in
`streams/`:

- **`Stream.toList()` (Java 16)** — `StreamToListExample`: players' names by
  goals descending, as an unmodifiable list.
- **`Stream.mapMulti()` (Java 16)** — `StreamMapMultiExample`: flattens each
  team into its players' names.
- **`Collectors.teeing()` (Java 12)** — `CollectorsTeeingExample`: total and
  average league goals in a single pass.
- **`takeWhile()` / `dropWhile()` (Java 9)** — `TakeWhileDropWhileExample`:
  splits the goals-descending player list at a threshold.
- **`Stream.iterate()` predicate overload (Java 9)** —
  `StreamIterateExample`: matchweeks 1–38 with no explicit `limit()`.
- **`Stream.ofNullable()` (Java 9)** — `StreamOfNullableExample`: nullable
  player lookup as a zero-or-one-element stream.
- **Sequenced-collection stream view (Java 21)** —
  `SequencedCollectionStreamExample`: teams last-to-first via `reversed()`.
- **`Collectors.toUnmodifiableList()` (Java 10)** —
  `CollectorsGroupingByExample`: players grouped by team into unmodifiable
  lists (backs stage 4's `GET /api/streams/grouped-by-team`).

**Collections (stage 6)** — one example class per feature in `collections/`:

- **Immutable collection factories (Java 9)** — `ImmutableFactoriesExample`:
  the league's top three scorers built as `List.of()`, `Set.of()`, and
  `Map.of()` collections, each rejecting mutation with
  `UnsupportedOperationException`.
- **Sequenced Collections (Java 21)** — `SequencedCollectionsExample`: the
  season's matches ordered by date, with `getFirst()`, `getLast()`, and a
  `reversed()` view.

**Records and language basics (stage 7)** — one example class per feature in
`var/`, `text_blocks/`, and `records/`:

- **`var` local type inference (Java 10)** — `VarLocalInferenceExample`:
  the same "more assists than goals" player query run once with `var`
  locals and once with explicit types, proving they return identical
  results.
- **Text blocks (Java 15)** — `MatchdayReportExample`: a multi-line
  matchday report built from a `Match` with `String.formatted()`.
- **Records with compact constructors (Java 16)** — `PlayerSummaryExample`:
  its nested `PlayerSummary` record validates `goals`/`assists` are
  non-negative in a compact constructor, throwing `IllegalArgumentException`
  otherwise; `findById()` looks one up by dataset position (backs stage 7's
  `GET /api/records/player/{id}`).

**Sealed types and pattern matching (stage 8)** — one example class per
feature in `sealed/`, `pattern_matching/`, and `switch_expressions/`:

- **Sealed interfaces (Java 17)** — `MatchOutcomeExample`: models match
  results as a sealed `MatchOutcome` hierarchy permitting exactly
  `HomeWin`, `AwayWin`, and `Draw`; `outcomeOf()` classifies any `Match`
  into one of them.
- **`instanceof` pattern matching (Java 16)** —
  `InstanceofPatternMatchingExample`: describes an object that may be a
  `Player`, `Team`, or `Match` via pattern variables, with no explicit
  casts.
- **`switch` pattern matching with record deconstruction (Java 21)** —
  `SwitchPatternMatchingExample`: summarises each `MatchOutcome` by
  deconstructing its components in `switch` cases — exhaustive over the
  sealed hierarchy with no `default` branch.
- **Arrow `switch` expressions with `yield` (Java 14)** —
  `ArrowSwitchExample`: maps a player's position to a category with
  `switch` as an expression, including a multi-statement `yield` branch
  for unknown positions.

**Concurrency (stage 9)** — one example class per feature in `concurrency/`:

- **Virtual threads (Java 21)** — `VirtualThreadsExample`: runs three
  Premier League queries concurrently on
  `Executors.newVirtualThreadPerTaskExecutor()`, each task recording
  `Thread::isVirtual` from inside to prove it ran on a virtual thread.
- **Structured concurrency (Java 21, preview — JEP 453)** —
  `StructuredConcurrencyExample`: forks two dataset queries as subtasks of
  one `StructuredTaskScope.ShutdownOnFailure` scope that succeeds or fails
  as a unit. Lives in the dedicated `src/preview/java` source set — the
  only code in the project compiled with `--enable-preview`, keeping the
  flag's blast radius to this one class.
- **`Thread::isVirtual` behind the API (Java 21)** — `ThreadInfoExample`:
  reports the calling thread's name and virtual-ness; backs the stage-9
  endpoint demonstrating Boot 4's one-property Loom payoff
  (`spring.threads.virtual.enabled=true`).

**Other**

- **Records (Java 16)** — `data.Player`, `data.Team`, and `data.Match`
  model the shared dataset as records (stage 2). Record patterns are
  covered by stage 8's `SwitchPatternMatchingExample`.
- **Immutable collection factories (Java 9)** — `data.PremierLeagueDataBase`
  builds its lists with `List.of`.

**Optional (stage 5)** — one example class per feature in `optional/`,
against `Optional<Player>` lookups over the shared dataset:

- **`Optional.or()` (Java 9)** — `OptionalOrExample`: a team with no top
  scorer of its own falls back to the league-wide top scorer.
- **`Optional.ifPresentOrElse()` (Java 9)** — `OptionalIfPresentOrElseExample`:
  runs a present action for a known player, an empty action otherwise.
- **`Optional.isEmpty()` (Java 11)** — `OptionalIsEmptyExample`: direct
  emptiness check for whether a team has a top scorer.
- **`Optional.stream()` (Java 9)** — `OptionalStreamExample`: flattens
  several teams' top-scorer lookups, dropping the empties.

**API layer** — thin controllers in `api/` exposing selected examples over
HTTP, documented via springdoc-openapi:

- **`StreamsController`** (stage 4) — `GET /api/streams/top-scorers` and
  `GET /api/streams/grouped-by-team`, delegating straight to
  `StreamToListExample`/`CollectorsGroupingByExample`. No feature logic of
  its own (thin-controller rule).
- **`OptionalController`** (stage 5) — `GET /api/optional/team-top-scorer`,
  delegating straight to `OptionalOrExample.topScorerOfTeam()` and mapping
  an absent result to a 404 `ProblemDetail`.
- **`RecordsController`** (stage 7) — `GET /api/records/player/{id}`,
  delegating straight to `PlayerSummaryExample.findById()` and mapping an
  unknown id to a 404 `ProblemDetail`.
- **`ConcurrencyController`** (stage 9) — `GET /api/concurrency/thread-info`,
  delegating straight to `ThreadInfoExample.currentThreadReport()`; with
  `spring.threads.virtual.enabled=true` the report proves the request was
  served on a virtual thread.

See `specs/modern-java-features-spec.md` for the full coverage list this
project works towards.

## Development Workflow

Work proceeds stage by stage on `feature/stage-<N>/<slug>` branches per
`specs/stages/`, following Red-Green TDD (see `CLAUDE.md`).

## Contributing

This is a personal learning project; see `CLAUDE.md` for the hard rules
governing any contribution.
