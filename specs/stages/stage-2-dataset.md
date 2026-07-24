# Stage 2: Shared Dataset

**Branch:** `feature/stage-2/dataset`

## Goal

Build the shared Premier League domain model and in-memory dataset that every
later feature example queries against.

## Scope

In: `uk.co.stefirby.java.features.data` — `Player`, `Team`, `Match`, `PremierLeagueDataBase`.
Out: any feature example that consumes the dataset (stage 3+).

## Acceptance Criteria

ACs are in EARS format per `specs/ears-conventions.md`; `[AUTO]`/`[MANUAL]`
marks the verification route.

- [ ] `STAGE-2-AC-01` [AUTO] — The `data.Player` type shall expose name,
      team, position, nationality, goals, assists, appearances, and
      minutesPlayed.
- [ ] `STAGE-2-AC-02` [AUTO] — The `data.Team` type shall expose name,
      stadium, manager, and founded year.
- [ ] `STAGE-2-AC-03` [AUTO] — The `data.Match` type shall expose homeTeam,
      awayTeam, homeGoals, awayGoals, and date.
- [ ] `STAGE-2-AC-04a` [AUTO] — `PremierLeagueDataBase.getAllPlayers()`,
      `getAllTeams()`, and `getAllMatches()` shall return non-empty lists
      backed purely by in-memory data — no I/O, no database.
- [ ] `STAGE-2-AC-04b` [AUTO] — If a caller attempts to mutate a list
      returned by `PremierLeagueDataBase`, then the list shall throw
      `UnsupportedOperationException`.
- [ ] `STAGE-2-AC-05` [AUTO] — Every `Player.team` and every `Match`
      home/away team shall match a team name present in `getAllTeams()`.
- [ ] `STAGE-2-AC-06` [AUTO] — The dataset shall contain at least 4 teams
      and at least 3 players per team, so grouping/sorting/comparison
      examples are meaningful rather than trivial.

## Definition of Done

All boxes above checked, `./gradlew test` green, `README.md`/`RUNBOOK.md`
updated via the `finish-stage` skill, PR opened against `main`.
