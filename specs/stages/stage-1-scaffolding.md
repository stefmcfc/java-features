# Stage 1: Scaffolding

**Branch:** `feature/stage-1/scaffolding`

## Goal

Stand up the Gradle build and package skeleton so every later stage has
somewhere to land: Java 25 toolchain, Spring Boot 4.1.0 wired up (even though
no real endpoint exists yet), and Spock available as the test framework.

## Scope

In: `build.gradle.kts`, `settings.gradle.kts`, a minimal Spring Boot
application class, empty package skeleton, one smoke-test spec, the JaCoCo
90% coverage gate.
Out: any real feature example, any real REST endpoint, the PL dataset
(stage 2).

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route.

- [ ] `STAGE-1-AC-01` [AUTO] — The Gradle build shall declare a Java
      toolchain at language version 25; the smoke spec shall assert the
      test JVM reports feature version 25
      (`Runtime.version().feature() == 25`).
- [ ] `STAGE-1-AC-02` [AUTO] — The Gradle build shall apply the `java`,
      `groovy`, and `org.springframework.boot` (version `4.1.0`) plugins,
      plus whatever companion plugin/BOM Spring Boot 4.1 conventions
      require, with a starter dependency sufficient to boot an application
      context (e.g. `spring-boot-starter-web`) — verified transitively by
      the `STAGE-1-AC-04` context-load spec compiling and passing.
- [ ] `STAGE-1-AC-03` [AUTO] — The Gradle build shall place Spock (at a
      version compatible with Spring Boot 4.1's Groovy) on the test
      classpath and recognise `src/test/groovy` as a test source set —
      verified by the smoke spec being discovered and run by
      `./gradlew test`.
- [ ] `STAGE-1-AC-04` [AUTO] — When `./gradlew test` runs the
      `@SpringBootTest` smoke spec, the minimal application class under
      `uk.co.stefirby.java.features` shall load its application context
      successfully.
- [ ] `STAGE-1-AC-05a` [AUTO] — When `./gradlew build` is invoked, the
      build shall succeed.
- [ ] `STAGE-1-AC-05b` [MANUAL] — The build and source shall contain no
      `javax.*` imports and no deprecated Spring Boot 3 idioms — verified
      by inspection during review (automatable later via ArchUnit,
      `FUTURE-14`).
- [ ] `STAGE-1-AC-06` [AUTO] — The source tree shall contain every package
      in the Proposed Package Layout (`streams`, `collections`, `records`,
      `sealed`, `pattern_matching`, `switch_expressions`, `text_blocks`,
      `var`, `optional`, `concurrency`, `httpclient`, `data`, `api`,
      `qAnda`) under `uk.co.stefirby.java.features` — asserted by a Spock
      spec walking the package directories.
- [ ] `STAGE-1-AC-07` [AUTO] — The Gradle build shall enforce a minimum of
      90% line coverage via JaCoCo verification wired into
      `./gradlew check`; if coverage falls below 90%, then the build shall
      fail. Classes with no meaningful logic to test may be excluded via an
      explicit exclusion list in `build.gradle.kts`, per CLAUDE.md's
      coverage rule.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
