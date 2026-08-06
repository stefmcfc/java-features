# Stage 16: Observability

**Branch:** `feature/stage-16/observability`

## Goal

Give the app an operational surface (`FUTURE-21`): Spring Boot Actuator
health/info/metrics endpoints, Micrometer request metrics, the Boot 4.0
OpenTelemetry starter for OTLP export, and Boot 4.1's observability
context propagation demonstrated on an `@Async` dataset query — finally
giving `RUNBOOK.md` real operational content to document.

## Scope

In: the Actuator and OpenTelemetry starters in `build.gradle.kts`,
endpoint exposure in `application.yml`, and one small `@Async`
dataset query in `uk.co.stefirby.java.features.concurrency` showing
observation context propagating onto the async thread. Exporting to a
live OTLP collector is configuration-only — no collector is run in the
build; specs assert against the auto-configured beans and local
endpoints.

Out: custom metrics/meters beyond what Actuator provides out of the box,
dashboards, alerting, and any external infrastructure. The `qAnda`
package and topic examples are untouched except for the one `@Async`
addition.

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route. Ubiquitous requirement for every AC below:
observability is additive — every pre-existing spec passes unchanged, and
no feature logic moves into Actuator-related configuration.

- [ ] `STAGE-16-AC-01` [AUTO] — When `GET /actuator/health` is requested,
      the API shall respond 200 with status `UP`.
- [ ] `STAGE-16-AC-02` [AUTO] — When `GET /actuator/info` is requested,
      the API shall respond 200 with build/application information
      populated from the Gradle build.
- [ ] `STAGE-16-AC-03` [AUTO] — When at least one REST request has been
      served, `GET /actuator/metrics/http.server.requests` shall respond
      200 with a non-zero count — proving Micrometer instruments the
      HTTP layer automatically.
- [ ] `STAGE-16-AC-04` [AUTO] — Where the `spring-boot-starter-opentelemetry`
      is on the classpath, the application context shall contain the
      auto-configured OpenTelemetry SDK beans, with OTLP export
      configurable via properties alone — verified by a Spock spec
      asserting the beans' presence (no live collector in the build).
- [ ] `STAGE-16-AC-05` [AUTO] — When the `concurrency` package's `@Async`
      dataset query runs inside an active observation, the observation
      context shall be visible from the async thread — demonstrating
      Boot 4.1's `@Async` context propagation alongside the stage-9
      virtual-threads material.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
