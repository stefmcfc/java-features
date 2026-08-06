package uk.co.stefirby.java.features.random;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;
import uk.co.stefirby.java.features.data.Team;

/**
 * Java 17: the {@link RandomGenerator} interface and its
 * {@link RandomGeneratorFactory} replace the single fixed {@code Random}
 * algorithm with a pluggable family, all reachable through the same
 * {@code of(...)} entry points. A seeded generator obtained via
 * {@link RandomGeneratorFactory#of(String)}{@code .create(seed)} makes this
 * matchweek simulator reproducible; picking a fresh, unseeded generator via
 * {@link RandomGenerator#of(String)} is how a random seed is minted when
 * none is supplied. Java 21's {@link Math#clamp(int, int, int)} keeps every
 * rolled scoreline inside a plausible range.
 */
public class MatchweekSimulatorExample {

    private static final String ALGORITHM = "L64X128MixRandom";
    private static final int MIN_GOALS = 0;
    private static final int MAX_GOALS = 6;

    /**
     * One simulated fixture's scoreline.
     *
     * @param homeTeam  the home club's name
     * @param awayTeam  the away club's name
     * @param homeGoals the home team's simulated, clamped score
     * @param awayGoals the away team's simulated, clamped score
     */
    public record SimulatedFixture(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
    }

    /**
     * A full simulated matchweek, tagged with the seed that produced it so
     * the run can be reproduced.
     *
     * @param seed     the seed the fixtures were simulated with
     * @param fixtures the matchweek's simulated fixtures, in dataset team order
     */
    public record SimulatedMatchweek(long seed, List<SimulatedFixture> fixtures) {
    }

    /**
     * @param seed the seed to simulate with; the same seed always yields
     *             the same fixtures
     * @return the seed and its simulated matchweek fixtures
     */
    public static SimulatedMatchweek simulate(long seed) {
        return new SimulatedMatchweek(seed, simulateMatchweek(seed));
    }

    /**
     * Pairs the dataset's teams into fixtures and rolls a clamped scoreline
     * for each, using a generator seeded deterministically from {@code seed}.
     *
     * @param seed the seed to simulate with; the same seed always yields
     *             the same fixtures
     * @return the matchweek's simulated fixtures, in dataset team order
     */
    public static List<SimulatedFixture> simulateMatchweek(long seed) {
        RandomGenerator random = RandomGeneratorFactory.of(ALGORITHM).create(seed);
        List<Team> teams = PremierLeagueDataBase.getAllTeams();
        List<SimulatedFixture> fixtures = new ArrayList<>();
        for (int i = 0; i + 1 < teams.size(); i += 2) {
            Team home = teams.get(i);
            Team away = teams.get(i + 1);
            fixtures.add(new SimulatedFixture(home.name(), away.name(), rollGoals(random), rollGoals(random)));
        }
        return List.copyOf(fixtures);
    }

    /**
     * @return a freshly minted, unseeded seed to simulate with when the
     *         caller has not requested a specific one
     */
    public static long randomSeed() {
        return RandomGenerator.of(ALGORITHM).nextLong();
    }

    private static int rollGoals(RandomGenerator random) {
        int rawGoals = random.nextInt(-2, 9);
        return Math.clamp(rawGoals, MIN_GOALS, MAX_GOALS);
    }

    public static void main(String[] args) {
        long seed = randomSeed();
        System.out.println("Seed: " + seed);
        simulateMatchweek(seed).forEach(System.out::println);
    }
}
