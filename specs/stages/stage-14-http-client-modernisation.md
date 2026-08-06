# Stage 14: HTTP Client Modernisation

**Branch:** `feature/stage-14/http-client-modernisation`

## Goal

Bring the `httpclient` package up to the Boot 4 generation's client
features: replace stage 10's hand-wired `HttpServiceProxyFactory` with
Boot 4.0's HTTP service client auto-configuration (`FUTURE-29`), keeping
the manual variant alongside for the old-way/new-way contrast, and close
the SSRF attack class on all outbound clients with Boot 4.1's
`InetAddressFilter` (`FUTURE-30`).

## Scope

In: `uk.co.stefirby.java.features.httpclient` (the auto-configured client
group and its comparison against the manual wiring), a small Spring
configuration carrying the `@ImportHttpServices` declaration and the
`InetAddressFilter` bean, and `application.yml` (the client
group's base-URL property). Check the current Boot 4.1 reference docs for
the exact group/property names before writing the wiring — this is
new-generation API with little tutorial coverage.

Out: removing the stage-10 manual `HttpExchangeExample` — it stays as the
contrast piece. No new endpoints; the existing stage-4 top-scorers
endpoint remains the target payload.

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route. Ubiquitous requirement for every AC below:
feature logic stays in the `httpclient` package behind static methods or
injectable clients; nothing moves into `api/` controllers.

- [ ] `STAGE-14-AC-01` [AUTO] — Where the `TopScorersExchange` interface
      is declared to Boot via `@ImportHttpServices` with its base URL
      supplied through configuration properties (not code), the
      application context shall contain a Boot-built client bean for it,
      with no `HttpServiceProxyFactory` wiring written by hand for that
      bean.
- [ ] `STAGE-14-AC-02` [AUTO] — When the auto-configured client calls the
      stage-4 top-scorers endpoint, it shall return the same payload as
      the stage-10 manual `HttpExchangeExample` variant — proving the
      auto-configuration replaced only the wiring, not the behaviour.
- [ ] `STAGE-14-AC-03` [AUTO] — The stage-10 manual variant
      (`HttpExchangeExample` and its spec) shall remain present and
      passing unchanged, so the two wiring styles stay comparable side by
      side.
- [ ] `STAGE-14-AC-04` [AUTO] — Where an `InetAddressFilter` bean is
      registered, when an outbound client request resolves to a
      disallowed address, the client shall reject the request instead of
      connecting.
- [ ] `STAGE-14-AC-05` [AUTO] — While the `InetAddressFilter` is active,
      requests to permitted addresses (the app's own test-server address)
      shall continue to succeed — the existing client specs pass with the
      filter in place.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
