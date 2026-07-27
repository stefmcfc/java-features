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

Not yet applicable — no feature example classes exist until stage 2 onward.
Each example, once added, exposes a `main` method runnable directly.

## Running the Spring Boot API

```
./gradlew bootRun
```

Starts `uk.co.stefirby.java.features.JavaFeaturesApplication`. No REST
endpoints are wired up yet (stage 1 only proves the application context
loads).

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
