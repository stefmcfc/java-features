# Stage 15: Configuration and Security

**Branch:** `feature/stage-15/config-and-security`

## Goal

Demonstrate the idiomatic Boot 4 configuration style —
`@ConfigurationProperties` constructor-bound onto an immutable record
(`FUTURE-17`) — with the toggleable Spring Security 7 exploration
(`FUTURE-16`) as its first customer: a `security.enabled` flag,
defaulting to off, that switches HTTP Basic protection over the whole
REST layer without disturbing any existing spec.

## Scope

In: a new configuration-properties record (e.g.
`SecurityProperties(boolean enabled)`) registered via constructor
binding, a Spring Security 7 configuration guarded by
`@ConditionalOnBooleanProperty` (check current Boot 4.1 docs for the
idiomatic conditional before writing it), HTTP Basic as the first
mechanism, and `application.yml`. The properties record lives
with the security configuration, not in a topic package — it is Spring
wiring, not a Java feature example.

Out: bearer tokens/JWT (graduate later per `FUTURE-16`'s note), method
security, and any change to controller code — security wraps the REST
layer from outside; controllers stay thin and untouched.

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route. Ubiquitous requirement for every AC below:
with the flag at its default (off), every pre-existing integration spec
passes unchanged — zero-setup examples and Swagger UI stay the default
experience.

- [ ] `STAGE-15-AC-01` [AUTO] — The `security.enabled` flag shall bind
      onto an immutable `@ConfigurationProperties` record via constructor
      binding, and the bound record shall be the single source the
      security configuration consults — verified by a Spock spec
      asserting the bound values for both property states.
- [ ] `STAGE-15-AC-02` [AUTO] — While `security.enabled` is absent or
      false, the API shall serve every endpoint (including
      `/v3/api-docs` and Swagger UI) without authentication.
- [ ] `STAGE-15-AC-03` [AUTO] — While `security.enabled` is true, if a
      request arrives without credentials, then the API shall respond
      401.
- [ ] `STAGE-15-AC-04` [AUTO] — While `security.enabled` is true, when a
      request supplies valid HTTP Basic credentials, the API shall serve
      it exactly as in the unsecured state.
- [ ] `STAGE-15-AC-05` [AUTO] — Where the flag is supplied through an
      environment variable (`SECURITY_ENABLED`), Boot's relaxed binding
      shall apply it identically to a properties-file setting — verified
      by a Spock spec binding from an environment-style property source.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
