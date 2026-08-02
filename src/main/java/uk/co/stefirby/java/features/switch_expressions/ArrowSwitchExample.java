package uk.co.stefirby.java.features.switch_expressions;

import uk.co.stefirby.java.features.data.Player;
import uk.co.stefirby.java.features.data.PremierLeagueDataBase;

/**
 * Java 14: {@code switch} as an expression with arrow labels — no
 * fall-through, multiple labels per case, and {@code yield} to produce a
 * value from a multi-statement branch.
 */
public class ArrowSwitchExample {

    /**
     * @param position a playing position, e.g. {@code "Forward"}
     * @return the position's broad category; unknown positions are
     *         normalised and reported via the multi-statement
     *         {@code yield} branch
     */
    public static String categorise(String position) {
        return switch (position) {
            case "Goalkeeper", "Defender" -> "Defence";
            case "Midfielder" -> "Midfield";
            case "Forward" -> "Attack";
            default -> {
                String normalised = position.strip();
                yield "Uncategorised: " + normalised;
            }
        };
    }

    public static void main(String[] args) {
        PremierLeagueDataBase.getAllPlayers().stream()
                .map(Player::position)
                .distinct()
                .forEach(position -> System.out.println(position + " -> " + categorise(position)));
        System.out.println("  Wing-back   -> " + categorise("  Wing-back  "));
    }
}
