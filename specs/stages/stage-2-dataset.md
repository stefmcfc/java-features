# Stage 2: Shared Dataset

**Branch:** `feature/stage-2/dataset`

## Goal

Build the shared Premier League domain model and in-memory dataset that every
later feature example queries against. `Player`, `Team`, and `Match` are
implemented as Java records (16) — confirmed compatible with the Stream/
Optional/Collections examples in later stages before committing to it further.

## Scope

In: `uk.co.stefirby.java.features.data` — `Player`, `Team`, `Match`, `PremierLeagueDataBase`.
Out: any feature example that consumes the dataset (stage 3+).

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route.

- [x] `STAGE-2-AC-01` [AUTO] — The `data.Player` record shall expose name,
      team, position, nationality, goals, assists, appearances, and
      minutesPlayed as components.
- [x] `STAGE-2-AC-02` [AUTO] — The `data.Team` record shall expose name,
      stadium, manager, and founded year as components.
- [x] `STAGE-2-AC-03` [AUTO] — The `data.Match` record shall expose homeTeam,
      awayTeam, homeGoals, awayGoals, and date as components.
- [x] `STAGE-2-AC-04a` [AUTO] — `PremierLeagueDataBase.getAllPlayers()`,
      `getAllTeams()`, and `getAllMatches()` shall return non-empty lists
      backed purely by in-memory data — no I/O, no database.
- [x] `STAGE-2-AC-04b` [AUTO] — If a caller attempts to mutate a list
      returned by `PremierLeagueDataBase`, then the list shall throw
      `UnsupportedOperationException`.
- [x] `STAGE-2-AC-05` [AUTO] — Every `Player.team` and every `Match`
      home/away team shall match a team name present in `getAllTeams()`.
- [x] `STAGE-2-AC-06` [AUTO] — The dataset shall contain at least 4 teams
      and at least 3 players per team, so grouping/sorting/comparison
      examples are meaningful rather than trivial.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
