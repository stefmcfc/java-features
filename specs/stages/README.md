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
| 1 | [scaffolding](stage-1-scaffolding.md) | Complete |
| 2 | [dataset](stage-2-dataset.md) | Complete |
| 3 | [streams](stage-3-streams.md) | Complete |
| 4 | [api-layer](stage-4-api-layer.md) | Complete |
| 5 | [optional](stage-5-optional.md) | Complete |
| 6 | [collections](stage-6-collections.md) | Complete |
| 7 | [records-and-basics](stage-7-records-and-basics.md) | Complete |
| 8 | [sealed-and-pattern-matching](stage-8-sealed-and-pattern-matching.md) | Complete |
| 9 | [concurrency](stage-9-concurrency.md) | Complete |
| 10 | [other-apis](stage-10-other-apis.md) | Complete |
| 11 | [streams-part-2](stage-11-streams-part-2.md) | Complete |
| 12 | [async-and-simulation](stage-12-async-and-simulation.md) | Complete |

Stage 4 is pulled forward (ahead of Optional/Collections/language features) to
match the spec's own "Next Steps": prove the Spring Boot 4.1 wiring end-to-end
on a couple of Streams endpoints before building out the rest.

`qAnda/` practice exercises are optional per the spec and aren't tracked as
blocking ACs — add one alongside a topic's examples in the same stage if it's
useful, skip it otherwise.
