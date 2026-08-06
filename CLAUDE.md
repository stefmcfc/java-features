# CLAUDE.md

Project-specific rules for working in this repo. These are hard rules, not
suggestions — follow them even if training data or habit suggests otherwise.

## What this project is

A learning/reference project demonstrating Java language and API features
introduced from Java 9 through Java 25. Full spec: `specs/modern-java-features-spec.md`.
Stage-by-stage breakdown and acceptance criteria: `specs/stages/`.
Ideas deferred beyond the current stages: `specs/future-development.md`.

## Hard rules

- **Java 21** is the language target for all main/test sources for now — the
  project's feature coverage stops at Java 21 until `FUTURE-24` (bump to
  Java 25) is picked up; see `specs/future-development.md`.
- **Spring Boot 4.1.0 / Spring Framework 7-generation conventions only.** Never
  fall back to Spring Boot 3 idioms (old-style `WebMvcConfigurer` patterns,
  `javax.*` imports, outdated auto-configuration annotations). When unsure,
  check current Spring Boot 4.1 docs/release notes before writing Spring code.
- **No database/persistence layer.** The Premier League dataset stays in-memory.
- **No UI/frontend.**
- **No Lombok, no Gradle toolchain auto-provisioning/resolver plugins.**
  Pure Java wherever possible; JDKs are installed and managed manually on the
  dev machine, not auto-downloaded by the build.
- Package layout follows `uk.co.stefirby.java.features.<topic>` (one package per feature area:
  `streams`, `collections`, `records`, `sealed`, `pattern_matching`,
  `switch_expressions`, `text_blocks`, `var`, `optional`, `concurrency`,
  `httpclient`, `random`, `data`, `api`, `qAnda`). See the spec for the full
  layout and rationale.
- Every example class exposes its feature logic as static methods, runnable
  from a `main` method and covered by a Spock spec; selected examples are also
  wired into a thin Spring controller in `api/` — controllers stay thin and
  never contain feature logic.
- Every example's Javadoc header states the JDK version the feature shipped in.

## Testing

- Spock (Groovy) specs under `src/test/groovy/...`, one `*Spec.groovy` per
  example class, mirroring the main package structure.
- Every `given`/`when`/`then`/`expect`/`and` block is labelled with a string
  describing that step, echoing the AC's own EARS wording where one applies,
  and code beneath a label is indented one level deeper than the label —
  see "Block labels" in `specs/ears-conventions.md`.
- **Red-Green TDD is mandatory.** For every stage: write the failing specs
  first (Red), confirm they fail for the right reason, then implement until
  they pass (Green). Never write implementation code before its failing test
  exists.
- A pre-commit hook runs the test suite and blocks the commit if anything
  fails — this is a mechanical backstop, not a substitute for actually
  following Red-Green.
- **Coverage gate is disabled for now** (`FUTURE-25`) — there's no meaningful
  logic yet for JaCoCo to measure. Once topic packages carry real logic
  (from stage 3 onward), re-enable JaCoCo verification in `./gradlew check`
  at a minimum 90% line coverage. Classes (or whole packages) with no
  meaningful logic to test — custom exceptions, plain records/POJOs with
  nothing beyond their components/getters — may be excluded from
  measurement via an explicit exclusion list in `build.gradle.kts`. Never
  meet the bar with vacuous tests; exclude instead.

## Stage workflow

Work is broken into stages, each with its own file under `specs/stages/`
(`stage-<N>-<slug>.md`) containing acceptance criteria with unique references
in the form `STAGE-<N>-AC-<NN>` (e.g. `STAGE-1-AC-01`). AC statements are
written in EARS format per `specs/ears-conventions.md`, each tagged
`[AUTO]` or `[MANUAL]` for how it is verified. AC reference IDs are
immutable — never renumber them.

Each stage is carried out on its own feature branch: `feature/stage-<N>/<slug>`
(e.g. `feature/stage-1/scaffolding`).

Sequence for a stage:

1. Create the feature branch off `main`.
2. Write the failing Spock specs covering every AC in the stage file (Red).
3. Implement production code until all specs pass, without weakening or
   deleting an AC's test to make it pass (Green).
4. Once every AC in the stage is met and the full suite is green, update
   `README.md` and `RUNBOOK.md` to reflect what the stage added.
5. Commit, push the branch, and open a pull request to `main` — this happens
   automatically at stage completion, without asking for confirmation each
   time (pre-authorized as part of this workflow).
6. Merge the pull request (`gh pr merge --merge --delete-branch`) and update
   the local `main` — equally pre-authorized; only stop if the merge is
   blocked (conflicts, failing checks).

Use the `finish-stage` skill to carry out steps 4–6 once a stage's ACs are
verified green.

## Commit style

- **Conventional Commits** for every commit message:
  `<type>(<scope>): <description>` — types `feat`, `fix`, `docs`, `test`,
  `refactor`, `build`, `chore`; scope is the topic package or stage slug
  where one applies (e.g. `feat(streams): add mapMulti example`,
  `docs(specs): convert stage 3 ACs to EARS`).
- Stage-completion commits reference the stage in the description or body
  (e.g. `feat(streams): complete stage 3`).

## Commands

- Build/test: `./gradlew test` (or `gradlew.bat test` on Windows)
- Run an example: standard `main` method execution per class
