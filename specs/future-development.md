# Future Development

Backlog of ideas that are **out of scope for the current stages** (see
`specs/stages/`) but worth doing later. Items here are deliberately loose —
when one is picked up, promote it to a proper stage file
(`specs/stages/stage-<N>-<slug>.md`) with acceptance criteria, and update any
hard rules it conflicts with in `CLAUDE.md` and the main spec at the same time.

References use the form `FUTURE-<NN>` so they can be cited in discussion the
same way stage ACs are.

## Data & persistence

- [ ] `FUTURE-01` — **Database-backed dataset.** Replace (or sit behind) the
      in-memory `PremierLeagueDataBase` with a real database initialised from
      the same data — e.g. Spring Data JDBC + H2, or Postgres via
      Testcontainers/Docker Compose. Keep the existing static-method API so
      every feature example is untouched; only the data source changes. Use
      Boot's Testcontainers **service connections** / Docker Compose support
      for the local/test wiring — it's the idiomatic Boot 4 mechanism for
      exactly this. *Conflicts with the "no database/persistence layer" hard
      rule in `CLAUDE.md` and the spec's Non-Goals — relax both when this
      starts.*
- [ ] `FUTURE-02` — **Externalised dataset as a stepping stone.** Before (or
      instead of) a full database, load the dataset from a classpath JSON/CSV
      resource at startup. Smaller lift than `FUTURE-01`, and doubles as a demo
      of `Files.readString()`/parsing. *Conflicts with `STAGE-2-AC-04a`'s
      "no I/O" wording — that AC would need a conscious revision.*
- [ ] `FUTURE-03` — **Live data via a real football API.** Use the
      `httpclient` package to pull real Premier League standings/results from a
      public API (e.g. football-data.org) and map them into the domain model —
      turns the HttpClient examples into something genuinely useful.

## Java feature coverage

The project's language target is Java 21 for now (see the hard rule in
`CLAUDE.md`) — deliberately, to de-risk Groovy/Spock compatibility on the
newest JDKs before committing to it. `FUTURE-04` through `FUTURE-10` are the
headline additions between 21 and 25, gated behind bumping the target;
`FUTURE-11`, `FUTURE-27`, and `FUTURE-28` need no bump:

- [ ] `FUTURE-24` — **Bump language target to Java 25.** Once Groovy/Spock
      and Spring Boot's toolchain are confirmed compatible with JDK 25, raise
      the Gradle toolchain (and the `CLAUDE.md` hard rule) from 21 to 25
      project-wide, then land `FUTURE-04` through `FUTURE-11` below on top of
      it.

- [ ] `FUTURE-04` — **Stream Gatherers** (Java 24, JEP 485) — custom
      intermediate stream operations (`Gatherers.windowFixed`, `fold`,
      `scan`, a hand-rolled gatherer). Natural extension of the `streams`
      package and arguably the biggest Stream API addition since Java 8.
- [ ] `FUTURE-05` — **Scoped Values** (Java 25, JEP 506) — the modern
      alternative to `ThreadLocal`, pairs naturally with the virtual-threads
      examples in `concurrency`.
- [ ] `FUTURE-06` — **Unnamed variables & patterns `_`** (Java 22, JEP 456) —
      small but ubiquitous; fits alongside the record/pattern-matching
      examples.
- [ ] `FUTURE-07` — **Flexible constructor bodies** (Java 25, JEP 513) —
      statements before `super(...)`; good candidate for a validation example
      in the domain model.
- [ ] `FUTURE-08` — **Compact source files & instance `main`** (Java 25,
      JEP 512) — the new beginner-friendly program form; a fun contrast piece
      given every example here uses classic `public static void main`.
- [ ] `FUTURE-09` — **Markdown documentation comments** (Java 23, JEP 467) —
      could be adopted across the whole project's Javadoc rather than being a
      single example.
- [ ] `FUTURE-10` — **Foreign Function & Memory API** (Java 22, JEP 454) —
      calling native code without JNI. Heavier lift, lower day-to-day value;
      keep last in this group.
- [ ] `FUTURE-11` — **`qAnda` practice exercises.** The package is in the
      layout but no stage builds it — one exercise per topic package, written
      after the topic's stage lands so the exercises track real coverage.
- [ ] `FUTURE-27` — **Small Java 9–21 API singles.** The leftovers after
      stages 11–12 close out the headline 9–21 gaps — filler-grade
      individually, but a themed grab-bag example (or `qAnda` exercises per
      `FUTURE-11`) could sweep them up: `Arrays.mismatch()` (9),
      `Objects.requireNonNullElse()` (9), `InputStream.transferTo()` (9),
      `Optional.orElseThrow()` no-arg (10), `Path.of()` (11),
      `Character.toString(int)` (11), `Files.mismatch()` (12), compact
      number formatting (12).
- [ ] `FUTURE-28` — **`HttpClient` WebSocket client (11).** The third face
      of the Java 11 HTTP client after stage 10's synchronous GET and stage
      12's `sendAsync()`. Needs a WebSocket endpoint to talk to, so pair it
      with a small Spring WebSocket/STOMP addition to the `api/` layer —
      e.g. streaming simulated match events from the stage-12 simulator.

## API layer

- [x] `FUTURE-12` — **OpenAPI documentation** (springdoc-openapi) for the REST
      layer, so the endpoint catalogue is browsable/testable via Swagger UI
      without adding a frontend of our own. *Promoted into stage 4 as
      `STAGE-4-AC-06`/`STAGE-4-AC-07` — tracked there now.*
- [x] `FUTURE-16` — **Spring Security exploration, toggleable.** *Promoted
      into stage 15 (`stage-15-config-and-security.md`) — tracked there
      now.* Secure the
      REST layer (start with HTTP Basic or an API key, graduate to bearer
      token/JWT) as a way of exploring Spring Security under Spring Boot 4.1
      conventions. Must be switchable via a feature flag — e.g. a
      `security.enabled` property (settable through an environment variable)
      with `@ConditionalOnProperty` on the security config — **defaulting to
      off**, so the examples and Swagger UI stay zero-setup and existing
      integration specs keep passing unchanged. Add specs for both states:
      endpoints open when disabled, 401 without credentials when enabled.

## Example execution model

- [ ] `FUTURE-26` — **Retire the per-class `main` method.** Every example
      class currently ships a `public static void main` per `CLAUDE.md`/the
      spec's Example Format Convention, so it's runnable standalone from the
      console. Once the Spock suite and the `api/` layer are mature enough to
      be the primary way examples get exercised, consider dropping `main` in
      favour of running examples via their spec or a dedicated API call, with
      any demo output routed through logging at a level controlled by
      `application.yml`/an environment variable rather than
      `System.out.println`. *Conflicts with the "runnable from a `main`
      method" hard rule in `CLAUDE.md` and the spec's Example Format
      Convention — revise both if this is picked up.*

## Spring Boot 4 / Framework 7 features

Same rationale as the Java 22–25 gap above: the project mandates Boot 4.1
idioms, so the features that are *new* in this Spring generation are worth
showcasing in their own right. (Virtual threads in Boot, `ProblemDetail`
error responses, and `RestClient`/`@HttpExchange` clients were promoted
straight into stages 9, 5, and 10 respectively — see `STAGE-9-AC-03`,
`STAGE-5-AC-05b`, `STAGE-10-AC-04`.)

- [x] `FUTURE-17` — **`@ConfigurationProperties` bound to records** —
      constructor binding onto an immutable record is the idiomatic Boot 4
      config style and a real-world payoff of the stage-7 records material.
      Natural first customer: the `security.enabled` toggle in `FUTURE-16`.
      *Promoted into stage 15 (`stage-15-config-and-security.md`) — tracked
      there now.*
- [ ] `FUTURE-18` — **First-class API versioning** — Spring Framework 7's
      native API version negotiation for controllers; demo with a v1/v2 of
      one existing endpoint. Little tutorial coverage exists yet, which makes
      it a good reference example. Boot 4.0 auto-configures it via
      `spring.mvc.apiversion.*` properties — the property-driven route is
      the idiomatic implementation when this is picked up.
- [ ] `FUTURE-19` — **Core resilience annotations** (`@Retryable`,
      `@ConcurrencyLimit`) — retry support moved into Spring Framework
      proper in this generation; pairs with the HTTP-client examples
      (retry a flaky call, cap concurrent calls). Ties into `FUTURE-03`.
- [x] `FUTURE-20` — **JSpecify null-safety annotations** — Framework 7
      adopted JSpecify across its codebase; adopt it in this project's
      packages too. Complements the `optional` package's story about
      modelling absence. *Promoted into stage 13
      (`stage-13-null-safety.md`) — tracked there now.*
- [x] `FUTURE-21` — **Actuator + Micrometer observability** — health, info,
      and metrics endpoints; near-zero effort and gives `RUNBOOK.md`
      something operational to document (which endpoints exist, what healthy
      looks like). Fold in the Boot 4.0 `spring-boot-starter-opentelemetry`
      (auto-configured OTLP metrics/traces export) and Boot 4.1's
      observability context propagation for `@Async` methods, which pairs
      with the `concurrency` package. *Promoted into stage 16
      (`stage-16-observability.md`) — tracked there now.*
- [x] `FUTURE-29` — **HTTP service client auto-configuration (Boot 4.0).**
      *Promoted into stage 14 (`stage-14-http-client-modernisation.md`) —
      tracked there now.*
      Stage 10's `HttpExchangeExample` wires `HttpServiceProxyFactory` +
      `RestClientAdapter` by hand; Boot 4.0 made that obsolete — declare the
      interface group with `@ImportHttpServices` and configure base URLs via
      properties, and Boot builds the client bean. Upgrade
      `TopScorersExchange` to the auto-configured style, keeping the manual
      variant alongside for the old-way/new-way contrast this project is
      built on. Ranks above most of this section: it modernises code the
      project already has.
- [x] `FUTURE-30` — **SSRF mitigation via `InetAddressFilter` (Boot 4.1).**
      *Promoted into stage 14 (`stage-14-http-client-modernisation.md`) —
      tracked there now.*
      One auto-configured bean filters outbound HTTP-client requests by
      resolved address, closing the SSRF class of attack. Slots straight
      into the `httpclient` package's client comparison, and becomes
      practically necessary if `FUTURE-03` (calling a real external API) is
      picked up — tie the two together when either starts.
- [ ] `FUTURE-31` — **gRPC auto-configuration (Boot 4.1).** Spring gRPC
      brings first-class gRPC server/client support to Boot. Headline
      feature of the release, but a second RPC surface is arguably
      off-mission for a REST-demo project — low priority, and skipping it
      deliberately (as was done for JPMS) is a legitimate outcome.

## Package layout

- [ ] `FUTURE-23` — **Dedicated `dates/` package.** An early draft of the main
      spec called out a `dates/` package for `java.time` examples (match
      dates, date-range filtering), but it never made it into the Proposed
      Package Layout or any stage. Promote if
      a dedicated `java.time` feature set is wanted; until then, date-range
      queries live wherever they naturally land (e.g. filtering `Match.date`
      inside `streams/` — stage 11's `LocalDate.datesUntil()` example lands
      there per this note, and would be the seed content for this package).

## Tooling & quality

- [ ] `FUTURE-25` — **Re-enable the JaCoCo coverage gate.** Stage 1 ships
      with no coverage enforcement (`STAGE-1-AC-07`) since the only
      production class at that point is the Spring Boot entrypoint, which has
      no meaningful logic to measure. Wire JaCoCo verification back into
      `./gradlew check` at CLAUDE.md's 90% line-coverage bar once a topic
      package (stage 3 onward) has real logic to test against.

- [ ] `FUTURE-13` — **CI with GitHub Actions** — run `./gradlew test` on every
      push/PR so the pre-commit hook isn't the only backstop.
- [ ] `FUTURE-14` — **ArchUnit tests** enforcing the structural rules that are
      currently only prose in `CLAUDE.md`: controllers stay thin and only call
      into topic packages, package layout matches the spec, no `javax.*`
      imports.
- [ ] `FUTURE-15` — **JMH microbenchmarks** comparing old-vs-new approaches
      where the difference is measurable (e.g. `Collectors.toList()` vs
      `Stream.toList()`, platform vs virtual threads under blocking load) —
      gives the "why the feature exists" narrative hard numbers.
- [ ] `FUTURE-22` — **GraalVM native image** of the Spring Boot app — Boot 4
      has first-class native support; instructive for seeing what AOT
      compilation demands (reflection/resource hints). Heaviest lift in this
      file and teaches Boot internals more than Java features — deliberately
      last.
