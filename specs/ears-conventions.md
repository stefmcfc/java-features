# EARS Conventions for Acceptance Criteria

Stage acceptance criteria are written in EARS (Easy Approach to Requirements
Syntax) so each AC translates mechanically into a Spock spec's
`given`/`when`/`then` blocks. This file defines how EARS is applied in this
repo — follow it when converting existing ACs or writing new ones.

## Patterns

Use the standard five, with a concrete named system (see rules below):

| Pattern | Template | Repo-flavoured example |
|---|---|---|
| Ubiquitous | The `<system>` shall `<response>` | The `PremierLeagueDataBase` shall return non-empty immutable lists from `getAllPlayers()` |
| Event-driven | When `<trigger>`, the `<system>` shall `<response>` | When `GET /api/streams/top-scorers` is requested, the API shall return players sorted by goals descending, as JSON |
| State-driven | While `<state>`, the `<system>` shall `<response>` | While `security.enabled` is false, the API shall serve every endpoint without authentication |
| Unwanted behaviour | If `<condition>`, then the `<system>` shall `<response>` | If an unknown team is requested, then the API shall respond 404 with a `ProblemDetail` body |
| Optional feature | Where `<feature is present>`, the `<system>` shall `<response>` | Where the preview flag for structured concurrency is enabled, the build shall compile the `concurrency` package |

Complex ACs may combine clauses (When … , if … , then …). Prefer the
simplest pattern that captures the requirement.

## Mapping to Spock

- **While/Where** (state, preconditions) → `given:` block
- **When** (trigger) → `when:` block
- **shall** (response) → `then:` assertions
- **If/then** (unwanted behaviour) → `when:` + `then:` with `thrown(...)` or
  error-status assertions

A ubiquitous requirement typically becomes a `then:`/`expect:`-only spec.

### Block labels

Every `given`/`when`/`then`/`expect`/`and` block carries a string label
describing that step in plain language — bare, unlabelled blocks aren't
used. Where a block maps directly onto an EARS clause (see table above),
the label echoes that clause's own wording, so the spec reads as the AC's
sentence split across blocks:

- `given "<state>":` — mirrors a While/Where clause, or states setup when
  the AC has no explicit precondition.
- `when "<trigger>":` — mirrors the When clause.
- `then "<response>":` — mirrors the shall clause.
- `and "<...>":` — a further assertion or action within the same phase;
  label it independently rather than leaving it bare.
- `expect "<response>":` — collapses when+then into one block for a direct,
  side-effect-free assertion; still labelled.

Code beneath a labelled block is indented one level deeper than the label,
so the label reads as a heading for the statements under it:

```groovy
def "STAGE-N-AC-NN: <spec name>"() {
    when: "GET /api/example is requested"
        def response = client.get().uri("/api/example").exchange()

    then: "the API responds 200"
        response.expectStatus().isOk()

    and: "the body is the stage-N example's result"
        response.expectBody(String).isEqualTo(expected)
}
```

## Verification markers

Every AC carries a marker immediately after its reference ID:

- `[AUTO]` — verified by the automated build: a Spock spec, or the build
  pipeline itself (compilation, `./gradlew check`, the coverage gate).
- `[MANUAL]` — verified by human review (e.g. code inspection). A `[MANUAL]`
  AC must state how it is checked, and should note the route to automating
  it where one exists (e.g. ArchUnit, `FUTURE-14`).

With no front-end in this project, `[AUTO]` is the default and the
overwhelming majority — treat any new `[MANUAL]` as something to justify.

## Conversion rules

1. **Reference IDs are immutable.** Keep `STAGE-<N>-AC-<NN>` exactly as-is —
   IDs are cross-referenced from `specs/future-development.md`, other stage
   files' scope notes, and the `finish-stage` skill. Conversion rewrites the
   statement *after* the ID; it never renumbers, merges, or deletes an ID.
2. **Keep the `- [ ]` checkbox form** — the `finish-stage` skill ticks these.
3. **Splitting:** if one prose AC contains several distinct requirements, use
   sub-letters under the same ID (`STAGE-3-AC-01a`, `STAGE-3-AC-01b`) rather
   than new numbers, so existing references stay valid.
4. **Name the system concretely** — the example class, the endpoint, the
   build, the dataset. Never a bare "the system".
5. **No weakening.** Every obligation in the original prose AC must survive
   conversion; if an AC is ambiguous, resolve it toward the stricter reading
   and note the decision in the stage file.
6. **Terse feature-label ACs** (e.g. "`Stream.mapMulti()` (16) example") must
   gain an observable behaviour against the Premier League dataset — an EARS
   statement needs a response that a Spock spec can assert. Pick a query in
   the spirit of the main spec's examples (top scorers, grouping by team,
   etc.).
7. **Repo-wide invariants stay factored out.** The "static method + `main`
   method + JDK-version Javadoc header + one Spock spec per class" convention
   (CLAUDE.md) applies to every example AC — state it once per stage as a
   ubiquitous requirement rather than repeating it inside each AC.
