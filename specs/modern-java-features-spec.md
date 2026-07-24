# Modern Java Features — Learning Project Spec

## Purpose
A personal-development companion project (same spirit as this `java-8-master` repo) that
demonstrates key language and API features introduced from **Java 9 through Java 25**. Each
feature gets a small, self-contained example — runnable standalone (a `main` method that prints
to the console) — and select examples are also exposed as REST endpoints via Spring Boot so
they can be called over HTTP, e.g. `GET /api/streams/top-scorers`.

## Goals
- One example class per feature (mirrors this repo's pattern: package = topic, class = example).
- Each example is runnable/callable on its own and prints or returns results that illustrate
  the feature.
- Comments/README per package explain *why* the feature exists and what problem it solves,
  not just *what* the syntax is.
- Reuse a small shared dataset — **Premier League stats** (players, teams, match results) — across
  examples so comparisons across versions are easy (e.g. "here's this query in Java 8 vs. Java 16
  vs. Java 21") and the data itself is more fun to poke at than generic sample records.
- Expose a subset of examples through a thin Spring Boot REST layer so features can be
  demonstrated over HTTP, not just via `main`.

## Non-Goals (for now)
- Not a full production application — this is a reference/demo project with an HTTP layer
  bolted on for convenience, not the other way around.
- No UI/frontend.
- No persistence layer/database — the Premier League dataset stays in-memory.

Deferred ideas (including a future database-backed dataset) are tracked in
`specs/future-development.md`.

## Conventions
- **Spring version:** Use **Spring Boot 4.1.0 / Spring Framework 4-generation conventions**
  throughout — this is a hard rule, not a suggestion. Do **not** fall back to Spring Boot 3 /
  older Spring Framework idioms (e.g. old-style `WebMvcConfigurer` patterns, javax.* imports,
  outdated auto-configuration annotations) even if training data or habit suggests them. When in
  doubt, check current Spring Boot 4.1 docs/release notes for the idiomatic approach before
  writing Spring code.

## Tech Stack
- **Language level:** Java 25 as the primary target.
- **Build tool:** Gradle (Kotlin DSL, `build.gradle.kts`).
- **Framework:** Spring Boot 4.1.0, following Spring 4-generation conventions (see Conventions
  above) — used only to expose selected examples as REST endpoints, not as the core of the project.
- **Testing:** Spock (Groovy-based spec framework). Requires the Gradle `groovy` plugin alongside
  `java`; test sources live in `src/test/groovy/...` as `*Spec.groovy` files.
- **Structure:** Single module to start (`src/main/java/uk/co/stefirby/java/features/...`), same package-per-topic
  layout as this repo, plus `src/test/groovy/...` for Spock specs.

## Proposed Package Layout
```
uk.co.stefirby.java.features
├── streams/            // Stream API additions post-Java 8 (toList, mapMulti, teeing, etc.)
├── collections/         // Sequenced Collections (21), List.of/Set.of/Map.of (9), immutable copies
├── records/              // Records (16), record patterns (21)
├── sealed/               // Sealed classes/interfaces (17)
├── pattern_matching/     // instanceof pattern matching (16), switch pattern matching (21)
├── switch_expressions/   // Arrow switch, yield, switch as expression (14)
├── text_blocks/          // Text blocks (15)
├── var/                  // Local variable type inference (10)
├── optional/             // Optional additions (9-11): or(), ifPresentOrElse, isEmpty, stream()
├── concurrency/          // Virtual threads / Project Loom (21), structured concurrency (preview)
├── httpclient/           // java.net.http.HttpClient (11)
├── data/                 // Shared Premier League domain model + in-memory dataset
├── api/                  // Spring Boot REST controllers exposing selected examples over HTTP
└── qAnda/                // Practice exercises, one per topic
```

## HTTP API Layer
A thin Spring Boot 4.1 layer sits on top of the plain-Java examples — it doesn't replace them.
Controllers in `uk.co.stefirby.java.features.api` call straight into the same static example methods used by
the `main`-based demos, so each feature stays runnable both ways. Rough shape:

```
GET /api/streams/top-scorers
GET /api/streams/grouped-by-team
GET /api/optional/team-top-scorer?team=Arsenal
GET /api/records/player/{id}
```

Keep controllers thin — data shaping/feature logic stays in the topic packages
(`streams/`, `optional/`, etc.); controllers just adapt them to HTTP. Use Spring Boot 4.1's
current conventions for routing/config (see Conventions section) rather than patterns carried
over from Spring Boot 3.

## Shared Dataset: Premier League Stats
Replaces the `Student`/`StudentDataBase` model from `java-8-master` with something more
relatable to iterate over in Stream/Optional examples.

```
uk.co.stefirby.java.features.data
├── Player        // name, team, position, nationality, goals, assists, appearances, minutesPlayed
├── Team          // name, stadium, manager, founded year
├── Match         // homeTeam, awayTeam, homeGoals, awayGoals, date (good for dates/ package too)
└── PremierLeagueDataBase   // static in-memory lists: getAllPlayers(), getAllTeams(), getAllMatches()
```

This gives natural example queries throughout the project: top scorers (`sorted`/`Comparator`),
players grouped by team or position (`Collectors.groupingBy`), average goals per team
(`Collectors.averagingInt` / `teeing`), a team's top scorer as an `Optional<Player>`, match
results filtered by date range (pairs well with the `dates/` package), etc.

## Versioning Strategy
Tag each example's Javadoc/comment header with the JDK version the feature shipped in, e.g.:
```java
/**
 * Java 16: Stream.toList() — shorthand for collect(Collectors.toList()),
 * returns an unmodifiable list.
 */
```
This keeps the "which version added this" context visible without needing separate modules per
JDK release.

## Feature Coverage List (starter — expand as you go)

**Streams (headline focus)**
- `Stream.toList()` (16)
- `Stream.mapMulti()` (16)
- `Collectors.teeing()` (12)
- `Stream.takeWhile()` / `dropWhile()` (9)
- `Stream.iterate()` with predicate overload (9)
- `Stream.ofNullable()` (9)
- Sequenced collection stream views (21)

**Language features**
- `var` local type inference (10)
- Text blocks (15)
- Records (16) + compact constructors, record patterns (21)
- Sealed classes/interfaces (17)
- Pattern matching for `instanceof` (16)
- Pattern matching for `switch` + record deconstruction (21)
- Enhanced/arrow `switch` expressions, `yield` (14)

**Collections**
- Immutable factory methods `List.of()`, `Set.of()`, `Map.of()` (9)
- Sequenced Collections: `getFirst()`, `getLast()`, `reversed()` (21)

**Optional**
- `Optional.or()`, `ifPresentOrElse()` (9)
- `Optional.isEmpty()` (11)
- `Optional.stream()` (9)

**Concurrency**
- Virtual threads (21)
- Structured concurrency (preview — note explicitly if using a preview API)

**Other notable APIs**
- `java.net.http.HttpClient` (11)
- `String` methods: `isBlank()`, `strip()`, `repeat()`, `lines()` (11)
- `Files.readString()` / `writeString()` (11)

## Example Format Convention
Keep the same lightweight style as `java-8-master`:
```java
package uk.co.stefirby.java.features.streams;

/**
 * Java 16: Stream.toList()
 */
public class StreamToListExample {

    public static List<String> topScorerNames() {
        return PremierLeagueDataBase.getAllPlayers().stream()
                .sorted(Comparator.comparingInt(Player::getGoals).reversed())
                .map(Player::getName)
                .toList();
    }

    public static void main(String[] args) {
        System.out.println(topScorerNames());
    }
}
```

Pulling the logic into a static method (rather than only inline in `main`) is what lets the
Spring controller and the Spock spec both call the same code the console demo does.

## Test Convention (Spock)
One `*Spec.groovy` per example class, mirroring the package structure under `src/test/groovy`:
```groovy
package uk.co.stefirby.java.features.streams

class StreamToListExampleSpec extends spock.lang.Specification {

    def "topScorerNames returns players sorted by goals descending"() {
        when:
        def result = StreamToListExample.topScorerNames()

        then:
        result == result.sort { a, b -> 0 } // placeholder — replace with real assertions
        !result.isEmpty()
    }
}
```

## Next Steps
1. Scaffold new Gradle project (`build.gradle.kts`) targeting Java 25 with the `java`, `groovy`,
   and `org.springframework.boot` (4.1.0) plugins.
2. Build the `Player`/`Team`/`Match`/`PremierLeagueDataBase` classes with a small hand-picked
   sample of real or plausible Premier League data (a handful of teams/players is plenty to start).
3. Start with the `streams/` package since that's the stated priority, then branch out.
4. Add one or two `api/` controllers (e.g. top scorers, team lookup) to prove out the Spring
   Boot 4.1 wiring end-to-end before adding more endpoints.
5. Add a Spock spec alongside each example class as it's written, rather than backfilling later.
6. Add a top-level README per package as coverage grows (optional, not required to start).
