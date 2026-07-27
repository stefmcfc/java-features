# Stage 10: Other Notable APIs

**Branch:** `feature/stage-10/other-apis`

## Goal

Cover the remaining notable API additions: `HttpClient`, new `String`
methods, and `Files` string helpers — and contrast the JDK client with
Spring's modern client-side HTTP options.

## Scope

In: `uk.co.stefirby.java.features.httpclient`, plus wherever the `String`/`Files` examples
land per the package layout (co-locate with an existing topic package if the
spec doesn't call out a dedicated one). The HttpClient/RestClient/
`@HttpExchange` example methods take the base URI as a parameter rather than
hardcoding `localhost`, so each stays runnable standalone from `main` against
any running instance, not just a test-fixture one.

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route. Ubiquitous requirement for every AC below:
each example class shall expose its feature logic as static methods, shall
provide a `main` method printing the result, and shall carry a Javadoc
header naming the JDK version; each AC is verified by a passing Spock spec.

- [ ] `STAGE-10-AC-01` [AUTO] — When the `java.net.http.HttpClient` (11)
      example issues a GET request (suggested target: the app's own
      stage-4 endpoint, so no external dependency is needed), it shall
      return the response status and body.
- [ ] `STAGE-10-AC-02` [AUTO] — When the `String` methods (11) example runs
      over PL text data, `isBlank()`, `strip()`, `repeat()`, and `lines()`
      shall each produce their asserted result.
- [ ] `STAGE-10-AC-03` [AUTO] — When the `Files.writeString()` /
      `readString()` (11) example writes a matchday report to a temporary
      file and reads it back, the content shall round-trip identically.
- [ ] `STAGE-10-AC-04` [AUTO] — When the same request is made via the raw
      JDK `HttpClient`, Spring's `RestClient`, and a declarative
      `@HttpExchange` interface (suggested target: the app's own stage-4
      `/api/streams/top-scorers` endpoint), each client shall return the
      same payload — each variant covered by its own Spock spec.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
