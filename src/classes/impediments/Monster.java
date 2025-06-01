package classes.impediments;

import classes.nonrooms.Player;

public class Monster extends Impediment {
    private int attackPower;
    private int healthPoints;

    public Monster(String name, String description, int attackPower, int healthPoints) {
        super(name, description, -attackPower); // Pass negative attackPower as hpImpact to Impediment constructor
        this.attackPower = attackPower;
        this.healthPoints = healthPoints;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void attack(Player player) {
        System.out.println("=== MONSTER ATTACK ===");
        System.out.println(getName() + " attacks!");

        int currentPlayerHp = player.getHp();
        player.setHp(currentPlayerHp - attackPower);

        System.out.println("You lost " + attackPower + " HP!");
        System.out.println("====================");
    }

    public void encounter() {
        System.out.println("=== MONSTER ENCOUNTERED ===");
        System.out.println("You encountered " + getName() + "!");
        System.out.println(getDescription());
        System.out.println("Monster HP: " + healthPoints);
        System.out.println("Attack Power: " + attackPower);
        System.out.println("==========================");
    }
}
