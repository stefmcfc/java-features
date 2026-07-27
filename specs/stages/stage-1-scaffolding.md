# Stage 1: Scaffolding

**Branch:** `feature/stage-1/scaffolding`

## Goal

Stand up the Gradle build and package skeleton so every later stage has
somewhere to land: Java 21 toolchain (see `FUTURE-24` for the planned move to
25), Spring Boot 4.1.0 wired up (even though no real endpoint exists yet),
and Spock available as the test framework.

## Scope

In: `build.gradle.kts`, `settings.gradle.kts`, a minimal Spring Boot
application class, empty package skeleton, one smoke-test spec, the JaCoCo
90% coverage gate.
Out: any real feature example, any real REST endpoint, the PL dataset
(stage 2).

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route.

- [x] `STAGE-1-AC-01` [AUTO] — The Gradle build shall declare a Java
      toolchain at language version 21; the smoke spec shall assert the
      test JVM reports feature version 21
      (`Runtime.version().feature() == 21`).
- [x] `STAGE-1-AC-02` [AUTO] — The Gradle build shall apply the `java`,
      `groovy`, and `org.springframework.boot` (version `4.1.0`) plugins,
      plus whatever companion plugin/BOM Spring Boot 4.1 conventions
      require, with a starter dependency sufficient to boot an application
      context (e.g. `spring-boot-starter-web`) — verified transitively by
      the `STAGE-1-AC-04` context-load spec compiling and passing.
- [x] `STAGE-1-AC-03` [AUTO] — The Gradle build shall place Spock (at a
      version compatible with Spring Boot 4.1's Groovy) on the test
      classpath and recognise `src/test/groovy` as a test source set —
      verified by the smoke spec being discovered and run by
      `./gradlew test`.
- [x] `STAGE-1-AC-04` [AUTO] — When `./gradlew test` runs the
      `@SpringBootTest` smoke spec, the minimal application class under
      `uk.co.stefirby.java.features` shall load its application context
      successfully.
- [x] `STAGE-1-AC-05a` [AUTO] — When `./gradlew build` is invoked, the
      build shall succeed.
- [x] `STAGE-1-AC-05b` [MANUAL] — The build and source shall contain no
      `javax.*` imports and no deprecated Spring Boot 3 idioms — verified
      by inspection during review (automatable later via ArchUnit,
      `FUTURE-14`).
- [x] `STAGE-1-AC-06` [AUTO] — The source tree shall contain every package
      in the Proposed Package Layout (`streams`, `collections`, `records`,
      `sealed`, `pattern_matching`, `switch_expressions`, `text_blocks`,
      `var`, `optional`, `concurrency`, `httpclient`, `data`, `api`,
      `qAnda`) under `uk.co.stefirby.java.features`, each containing a
      `package-info.java` (so the empty directory is tracked by git and has
      a home for that package's "why this feature exists" narrative) —
      asserted by a Spock spec walking the package directories.
- [x] `STAGE-1-AC-07` [AUTO] — The Gradle build shall not enforce a JaCoCo
      coverage gate in `./gradlew check` at this stage — the gate is
      deferred to `FUTURE-25` and re-enabled once a later stage's topic
      packages carry real logic to measure.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
