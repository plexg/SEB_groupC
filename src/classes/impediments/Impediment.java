package classes.impediments;

import classes.nonrooms.Player;

/**
 * The Impediment class represents obstacles or blockades in the Scrum escape game.
 * Impediments are triggered when a player gives an incorrect answer to a challenge.
 * They can display messages and affect the player's health points.
 */
public class Impediment {
    private String name;
    private String description;
    private int hpImpact;

    /**
     * Creates a new Impediment with the specified name, description, and HP impact.
     *
     * @param name The name of the impediment
     * @param description A description of the impediment
     * @param hpImpact The impact on player health points (negative for penalties)
     */
    public Impediment(String name, String description, int hpImpact) {
        this.name = name;
        this.description = description;
        this.hpImpact = hpImpact;
    }

    /**
     * Gets the name of the impediment.
     *
     * @return The impediment name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the impediment.
     *
     * @return The impediment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the HP impact of the impediment.
     *
     * @return The HP impact
     */
    public int getHpImpact() {
        return hpImpact;
    }

    /**
     * Applies the impediment effect to the player.
     * This will display the impediment message and adjust the player's health points.
     *
     * @param player The player to apply the impediment to
     */
    public void applyTo(Player player) {
        System.out.println("=== IMPEDIMENT ENCOUNTERED ===");
        System.out.println(name + ": " + description);

        if (hpImpact != 0) {
            int currentHp = player.getHp();
            player.setHp(currentHp + hpImpact);

            if (hpImpact < 0) {
                System.out.println("You lost " + Math.abs(hpImpact) + " HP!");
            } else {
                System.out.println("You gained " + hpImpact + " HP!");
            }
        }

        System.out.println("===============================");
    }
}
