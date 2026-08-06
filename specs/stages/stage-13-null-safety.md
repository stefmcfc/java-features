# Stage 13: Null Safety

**Branch:** `feature/stage-13/null-safety`

## Goal

Adopt JSpecify null-safety annotations across the project's production
packages, the way Spring Framework 7 did across its own codebase
(`FUTURE-20`): `@NullMarked` flips each package's default to non-null,
`@Nullable` marks the deliberate exceptions, and NullAway enforces the
declarations at build time so a nullability violation fails compilation
rather than surfacing as a runtime NPE.

## Scope

In: every production package under `uk.co.stefirby.java.features` (each
already has a `package-info.java` to carry `@NullMarked`), the few
genuinely nullable spots (e.g. `RandomController`'s optional `seed`
parameter, `StreamOfNullableExample`'s nullable lookup), and
`build.gradle.kts` (the `org.jspecify:jspecify` dependency plus NullAway
via the Error Prone compiler plugin, applied to main Java compilation
only — the Groovy/Spock test sources and the preview source set are out of
NullAway's reach and stay unanalysed).

Out: rewriting any example's logic. This stage annotates and enforces; it
does not change behaviour. No new packages, no new endpoints.

This is cross-cutting by design — landing it before stages 14–16 means
everything added afterwards is born null-marked.

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route. Ubiquitous requirement for every AC below:
no example's observable behaviour changes — the full pre-existing Spock
suite passes unmodified.

- [ ] `STAGE-13-AC-01` [AUTO] — The build shall declare the
      `org.jspecify:jspecify` dependency, and every production package
      under `uk.co.stefirby.java.features` shall carry `@NullMarked` on its
      `package-info.java` — verified by a Spock spec reflecting over each
      package's annotations.
- [ ] `STAGE-13-AC-02` [AUTO] — Where an API genuinely accepts or returns
      an absent value (at minimum `RandomController`'s omitted-`seed`
      parameter and `StreamOfNullableExample`'s nullable player lookup),
      the declaration shall carry `@Nullable` — verified by a Spock spec
      reflecting over those declarations.
- [ ] `STAGE-13-AC-03` [AUTO] — While NullAway (JSpecify mode, error
      severity) is wired into main Java compilation via the Error Prone
      plugin, the build shall compile the fully annotated production
      sources cleanly — verified by `./gradlew check` passing with the
      analysis active.
- [ ] `STAGE-13-AC-04` [MANUAL] — If a nullability violation is
      deliberately introduced (e.g. dereferencing a `@Nullable` value
      without a check), then `compileJava` shall fail with a NullAway
      error. Checked by hand once during the stage (introduce, observe the
      failure, revert); automating it would need a negative-compilation
      test harness, which is out of proportion here.
- [ ] `STAGE-13-AC-05` [AUTO] — The `optional` package's
      `package-info.java` shall note the relationship between JSpecify
      annotations (compile-time absence contracts) and `Optional`
      (runtime absence values) — verified by a Spock spec asserting the
      note's presence, echoing the stage-9 precedent of asserting on
      source documentation.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
