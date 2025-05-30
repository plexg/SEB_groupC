package classes.impediments;

import classes.nonrooms.Player;

/**
 * The Monster class represents enemies that players can encounter in the Scrum escape game.
 * Monsters can challenge players and affect their health points when encountered.
 * Monsters are a type of Impediment with additional attributes like health points and attack power.
 */
public class Monster extends Impediment {
    private int attackPower;
    private int healthPoints;

    /**
     * Creates a new Monster with the specified attributes.
     *
     * @param name The name of the monster
     * @param description A description of the monster
     * @param attackPower The monster's attack strength
     * @param healthPoints The monster's initial health points
     */
    public Monster(String name, String description, int attackPower, int healthPoints) {
        super(name, description, -attackPower); // Pass negative attackPower as hpImpact to Impediment constructor
        this.attackPower = attackPower;
        this.healthPoints = healthPoints;
    }

    /**
     * Gets the name of the monster.
     *
     * @return The monster's name
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * Gets the description of the monster.
     *
     * @return The monster's description
     */
    @Override
    public String getDescription() {
        return super.getDescription();
    }

    /**
     * Gets the monster's attack power.
     *
     * @return The attack power
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * Gets the monster's current health points.
     *
     * @return The current health points
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * Sets the monster's health points.
     *
     * @param healthPoints The new health points value
     */
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    /**
     * Attacks the player, dealing damage based on the monster's attack power.
     *
     * @param player The player being attacked
     */
    public void attack(Player player) {
        System.out.println("=== MONSTER ATTACK ===");
        System.out.println(getName() + " attacks!");

        int currentPlayerHp = player.getHp();
        player.setHp(currentPlayerHp - attackPower);

        System.out.println("You lost " + attackPower + " HP!");
        System.out.println("====================");
    }

    /**
     * Displays the monster's encounter message.
     */
    public void encounter() {
        System.out.println("=== MONSTER ENCOUNTERED ===");
        System.out.println("You encountered " + getName() + "!");
        System.out.println(getDescription());
        System.out.println("Monster HP: " + healthPoints);
        System.out.println("Attack Power: " + attackPower);
        System.out.println("==========================");
    }
}
