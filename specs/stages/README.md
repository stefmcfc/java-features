# Stage Breakdown

Working breakdown of `specs/modern-java-features-spec.md` into sequential
stages. Each stage is its own file here, carried out on a feature branch
`feature/stage-<N>/<slug>`, following Red-Green TDD and the workflow in
`CLAUDE.md`: write failing Spock specs for every AC first, implement until
green, then run the `finish-stage` skill to update docs and open a PR.

Acceptance criteria are referenced as `STAGE-<N>-AC-<NN>` and tracked as
checkboxes within each stage file — checked off by the `finish-stage` skill
once their spec passes.

| Stage | Slug | Status |
|---|---|---|
| 1 | [scaffolding](stage-1-scaffolding.md) | Not started |
| 2 | [dataset](stage-2-dataset.md) | Not started |
| 3 | [streams](stage-3-streams.md) | Not started |
| 4 | [api-layer](stage-4-api-layer.md) | Not started |
| 5 | [optional](stage-5-optional.md) | Not started |
| 6 | [collections](stage-6-collections.md) | Not started |
| 7 | [records-and-basics](stage-7-records-and-basics.md) | Not started |
| 8 | [sealed-and-pattern-matching](stage-8-sealed-and-pattern-matching.md) | Not started |
| 9 | [concurrency](stage-9-concurrency.md) | Not started |
| 10 | [other-apis](stage-10-other-apis.md) | Not started |

Stage 4 is pulled forward (ahead of Optional/Collections/language features) to
match the spec's own "Next Steps": prove the Spring Boot 4.1 wiring end-to-end
on a couple of Streams endpoints before building out the rest.

`qAnda/` practice exercises are optional per the spec and aren't tracked as
blocking ACs — add one alongside a topic's examples in the same stage if it's
useful, skip it otherwise.
