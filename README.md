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

Each package currently holds only a `package-info.java` scaffold — real
examples land stage by stage starting from `specs/stages/stage-2-dataset.md`.

## Running the Examples

Not yet applicable — no feature examples exist until stage 2 onward. The
minimal Spring Boot entry point is `uk.co.stefirby.java.features.JavaFeaturesApplication`.

## Running the REST API

```
./gradlew bootRun
```

Boots the (currently empty) Spring Boot 4.1 application context. No
endpoints are wired up yet.

## Testing

```
./gradlew test
```

Spock (Groovy) specs live under `src/test/groovy/...`, one `*Spec.groovy`
per example class. The JaCoCo coverage gate is intentionally disabled until
a later stage introduces real logic to measure (see `FUTURE-25`).

## Feature Coverage

No feature examples yet — stage 1 only stands up the build/package
scaffolding. See `specs/modern-java-features-spec.md` for the full coverage
list this project works towards.

## Development Workflow

Work proceeds stage by stage on `feature/stage-<N>/<slug>` branches per
`specs/stages/`, following Red-Green TDD (see `CLAUDE.md`).

## Contributing

This is a personal learning project; see `CLAUDE.md` for the hard rules
governing any contribution.
