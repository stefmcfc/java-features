# Runbook

## Overview

Operational notes for building, running, and testing this project. See
`README.md` for the project description and `CLAUDE.md` for the hard rules.

## Build & Compile

```
./gradlew build
```

Requires JDK 21 to be resolvable via the configured Gradle toolchain
(installed manually — no toolchain auto-provisioning per `CLAUDE.md`).

## Running Examples (CLI)

Every example class exposes a `main` method runnable directly. Build once,
then run any class on the compiled-classes classpath:

```
./gradlew build
java -cp build/classes/java/main uk.co.stefirby.java.features.streams.StreamToListExample
java -cp build/classes/java/main uk.co.stefirby.java.features.data.PremierLeagueDataBase
```

Stage 3 added the `streams/` examples: `StreamToListExample`,
`StreamMapMultiExample`, `CollectorsTeeingExample`,
`TakeWhileDropWhileExample`, `StreamIterateExample`,
`StreamOfNullableExample`, `SequencedCollectionStreamExample`, and
`CollectorsGroupingByExample`.

Stage 5 added the `optional/` examples: `OptionalOrExample`,
`OptionalIfPresentOrElseExample`, `OptionalIsEmptyExample`, and
`OptionalStreamExample`.

Stage 6 added the `collections/` examples: `ImmutableFactoriesExample` and
`SequencedCollectionsExample`.

## Running the Spring Boot API

```
./gradlew bootRun
```

Starts `uk.co.stefirby.java.features.JavaFeaturesApplication` on port 8080.
Stage 4 added the first REST endpoints, served by `api.StreamsController`:

```
GET /api/streams/top-scorers
GET /api/streams/grouped-by-team
```

Both delegate straight to the stage-3 static methods (`StreamToListExample`,
`CollectorsGroupingByExample`) — no logic lives in the controller.

Stage 5 added `api.OptionalController`:

```
GET /api/optional/team-top-scorer?team=<name>
```

Delegates straight to `OptionalOrExample.topScorerOfTeam()`. A known team
responds 200 with that team's top scorer as JSON; an unknown team responds
404 with an RFC 9457 `ProblemDetail` body — enabled via
`spring.mvc.problemdetails.enabled=true` in `application.properties`, so an
unhandled `ResponseStatusException` renders as a standard problem-details
document rather than Boot's default error page.

springdoc-openapi is wired in alongside them, so the API is self-documenting:

```
GET /v3/api-docs           # generated OpenAPI 3.x document
GET /swagger-ui/index.html # Swagger UI over that document
```

## Configuration

Enable the shared git hooks (test gate on commit) once per clone:

```
git config core.hooksPath githooks
```

## Testing

```
./gradlew test
```

Runs the Spock spec suite (`src/test/groovy`) via the JUnit Platform. The
pre-commit hook (`githooks/pre-commit`) runs this same command and blocks
the commit on failure.

## Troubleshooting

- **Build fails to resolve a JDK 21 toolchain:** confirm a JDK 21
  installation exists on the machine — this project does not use Gradle's
  toolchain auto-download/resolver plugins.
- **`spock-core`/`spock-spring` dependency resolution errors:** the pinned
  version is `2.4-groovy-5.0`, matching the Groovy 5.0.x managed by the
  Spring Boot 4.1 BOM; check `build.gradle.kts` if this drifts.

## Development Workflow

Each stage is implemented on its own `feature/stage-<N>/<slug>` branch per
`specs/stages/`, following Red-Green TDD: write failing Spock specs first,
then implement until green. The `finish-stage` skill handles doc updates
through PR creation once a stage's ACs are all met.

## Release / Merge Process

Stage branches are opened as pull requests against `main` once their full
test suite is green and the stage's acceptance criteria are checked off.
There is no separate release process yet — this is a reference project, not
a shipped artifact.
