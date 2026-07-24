# Stage 4: API Layer (proof of concept)

**Branch:** `feature/stage-4/api-layer`

## Goal

Prove the Spring Boot 4.1 REST wiring end-to-end on top of the stage-3
Streams examples, before adding more endpoints elsewhere — matches the
spec's own "Next Steps" ordering. Includes OpenAPI/Swagger documentation of
the API, wired in here so every controller added in later stages is
documented automatically.

## Scope

In: `uk.co.stefirby.java.features.api`, two controllers over existing stage-3 methods,
OpenAPI/Swagger UI via springdoc-openapi.
Out: any new feature logic — controllers only adapt what stage 3 already
built. No API security (tracked as `FUTURE-16` in
`specs/future-development.md`).

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route.

- [ ] `STAGE-4-AC-01` [AUTO] — When `GET /api/streams/top-scorers` is
      requested, the API shall respond 200 with the JSON result of the
      stage-3 top-scorers static method, delegating straight to it.
- [ ] `STAGE-4-AC-02` [AUTO] — When `GET /api/streams/grouped-by-team` is
      requested, the API shall respond 200 with the JSON result of the
      stage-3 grouped-by-team static method, delegating straight to it.
- [ ] `STAGE-4-AC-03` [MANUAL] — Controllers shall contain no
      data-shaping/feature logic of their own (thin-controller rule) —
      verified by inspection during review (automatable later via ArchUnit,
      `FUTURE-14`).
- [ ] `STAGE-4-AC-04` [MANUAL] — Routing/config shall use Spring Boot
      4.1-idiomatic patterns only (no `javax.*`, no deprecated
      `WebMvcConfigurer`-style config carried over from Boot 3) — verified
      by inspection during review.
- [ ] `STAGE-4-AC-05` [AUTO] — An integration-level Spock spec
      (`@SpringBootTest` with the Boot 4.1-idiomatic test client) shall
      request both endpoints and assert response status and shape.
- [ ] `STAGE-4-AC-06` [AUTO] — When `GET /v3/api-docs` is requested,
      springdoc-openapi (a version compatible with Spring Boot 4.1) shall
      return an OpenAPI 3.x document, and the default springdoc Swagger UI
      path shall respond successfully.
- [ ] `STAGE-4-AC-07` [AUTO] — The `/v3/api-docs` response shall list both
      stage-4 endpoint paths — asserted by a Spock spec, so the docs are
      proven to track the real controllers rather than being
      hand-maintained.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
