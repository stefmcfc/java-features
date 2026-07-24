---
name: finish-stage
description: This skill should be used when the user says "finish this stage", "close out the stage", "stage is done", "all ACs pass", "wrap up stage N", or when every acceptance criterion in a specs/stages/stage-<N>-*.md file has a passing Spock spec and the stage's feature branch is ready to land. Handles the doc-update-through-PR portion of this repo's stage workflow (see CLAUDE.md).
---

# Finish Stage

Carry out the completion half of this repo's Red-Green stage workflow: verify
the stage is actually done, update the project docs, then commit, push, and
open the pull request. Do not run this until every AC in the stage file has
a passing spec — this skill assumes Green has already been reached, it does
not get you there.

## Preconditions

Before doing anything else, confirm all of the following. If any check fails,
stop and report what's missing instead of proceeding.

1. Identify the active stage file under `specs/stages/stage-<N>-*.md` and its
   AC references (`STAGE-<N>-AC-<NN>`).
2. Confirm the current branch is `feature/stage-<N>/<slug>` matching that
   stage — not `main` and not a different stage's branch.
3. Run the full test suite (`./gradlew test`) and confirm it is green. If
   anything fails, stop here — go fix Red/Green first.
4. Re-read the stage file's AC list and check off each one against what was
   actually implemented. If an AC has no corresponding passing spec, or a
   spec was weakened/deleted to force a pass, stop and flag it rather than
   continuing.

## Steps

1. **Update `README.md`.** Add/adjust whatever sections are affected by this
   stage's changes (e.g. Project Structure, Running the Examples, Feature
   Coverage). Don't touch unrelated sections.
2. **Update `RUNBOOK.md`.** Add/adjust whatever's affected (e.g. new build/run
   steps, new endpoints, new troubleshooting notes). Don't touch unrelated
   sections.
3. **Update the stage file itself** under `specs/stages/` — mark each AC as
   done (e.g. change a `- [ ]` to `- [x]`, or however the file tracks status).
4. **Stage and commit.** Write the commit message in Conventional Commits
   style per CLAUDE.md's Commit style section (e.g.
   `feat(streams): complete stage 3 — post-Java-8 Stream API examples`),
   describing what the stage delivered and referencing the stage
   number/slug. The pre-commit hook will
   re-run the test suite; if it blocks the commit, treat that as a real
   failure and fix it rather than bypassing the hook.
5. **Push the branch** to the remote.
6. **Open a pull request to `main`** using `gh pr create`, with a summary
   that lists the ACs satisfied (by reference) and a test plan. This repo's
   convention is to do this automatically at stage completion without asking
   for confirmation each time — that authorization already covers this step.

## After opening the PR

Report the PR URL and a one-line summary of which stage/ACs it closes out.
Do not start the next stage automatically — that begins in a fresh
conversation turn per the usual stage-start sequence in CLAUDE.md.
